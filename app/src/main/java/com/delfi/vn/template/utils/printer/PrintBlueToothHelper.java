package com.delfi.vn.template.utils.printer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delfi.vn.template.R;
import com.delfi.vn.template.utils.printer.utils.ESCCommands;
import com.google.common.primitives.Bytes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PrintBlueToothHelper {
    private static PrintBlueToothHelper printBlueToothHelper;
    private Context context;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice device;
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;

    private IPrinterCallback printerCallback;
    private int type = 1;
    private byte[] printData;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            if (msg.what == 0) {
                Toast.makeText(context, "Thiết bị đã được kết nối!", Toast.LENGTH_SHORT).show();
                print();
            } else {
                printerCallback.error("Không thể kết nối đến thiết bị!\nBạn có muốn tiếp tục?");
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        break;
                    case BluetoothAdapter.STATE_ON:
                        device = null;
                        initPrinter(true);
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        break;
                }
            }
        }
    };

    private PrintBlueToothHelper() {
    }

    public static PrintBlueToothHelper getInstance() {
        if (printBlueToothHelper == null)
            printBlueToothHelper = new PrintBlueToothHelper();
        return printBlueToothHelper;
    }

    public void print(Context context, byte[] data, int type, IPrinterCallback printerCallback) {
        this.context = context;
        this.type = type;
        this.printerCallback = printerCallback;
        setPrintData(data);
        enableBluetooth();
    }

    private void enableBluetooth() {
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            boolean isEnabled = bluetoothAdapter.isEnabled();
            if (!isEnabled) {
                IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                this.context.registerReceiver(mReceiver, filter);
                bluetoothAdapter.enable();
            } else {
                initPrinter(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPrinter(boolean showChoose) {
        unregisterReceiver();
        if (device == null) {
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                List<BluetoothDevice> items = new ArrayList<>(pairedDevices);
                if (items.size() > 1 || showChoose)
                    showDialogChooseDevice(items);
                else
                    connectDevice(items.get(0));
            } else {
                printerCallback.error("Không có thiết bị nào được tìm thấy!\nBạn có muốn tiếp tục?");
            }
        } else {
            //showConfirmDevice(device);
            connectDevice(device);
        }
    }

    private void showDialogChooseDevice(List<BluetoothDevice> items) {
        Dialog dialog = new Dialog(this.context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(View.inflate(this.context, R.layout.dialog_choose_device, null));
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        DeviceAdapter adapter = new DeviceAdapter();
        adapter.setDataList(items);
        adapter.setListener(item -> {
            dialog.dismiss();
            connectDevice(item);
        });
        recyclerView.setAdapter(adapter);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.show();
    }

    private void connectDevice(BluetoothDevice mBluetoothDevice) {
        UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        mBluetoothConnectProgressDialog = ProgressDialog.show(this.context,
                "Connecting...", mBluetoothDevice.getName() + " : "
                        + mBluetoothDevice.getAddress(), true, false);
        new Thread(() -> {
            try {
                Log.d("connectDevice", "MAC: " + mBluetoothDevice.getAddress());
                mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
                bluetoothAdapter.cancelDiscovery();
                mBluetoothSocket.connect();
                mHandler.sendEmptyMessage(0);
                this.device = mBluetoothDevice;
            } catch (IOException eConnectException) {
                Log.d("connectDevice", "CouldNotConnectToSocket", eConnectException);
                closeSocket(mBluetoothSocket);
                mHandler.sendEmptyMessage(1);
                return;
            }
        }).start();
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d("closeSocket", "SocketClosed");
        } catch (IOException ex) {
            Log.d("closeSocket", "CouldNotCloseSocket");
        }
    }

    private void print() {
        try {
            Thread t = new Thread() {
                public void run() {
                    try {
                        List<Byte> bytes = new ArrayList<>();
                        OutputStream os = mBluetoothSocket.getOutputStream();
                        // Settings
                        bytes.addAll(Bytes.asList(ESCCommands.HW_INIT));
                        //bytes.addAll(Bytes.asList(ESCCommands.MACRO_START_END));
                        bytes.addAll(Bytes.asList(ESCCommands.TextAlignment.LEFT));
                        // Print data
                        bytes.addAll(Bytes.asList(getPrintData()));
                        //bytes.addAll(Bytes.asList(ESCCommands.MACRO_START_END));
                        //Loop as numer of times to execute macro
                        //bytes.addAll(ESCCommands.executeMacro(printLoop));
                        os.write(Bytes.toArray(bytes));
                        os.flush();
                        //Start send all data to printer
                        closeSocket(mBluetoothSocket);
                        try {
                            printerCallback.success();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        closeSocket(mBluetoothSocket);
                        printerCallback.error(e.getMessage());
                    }
                }
            };
            t.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getPrintData() {
        return printData;
    }

    public void setPrintData(byte[] printData) {
        this.printData = printData;
    }

    private void unregisterReceiver() {
        try {
            this.context.unregisterReceiver(mReceiver);
        } catch (Exception e) {
        }
    }

    public interface IPrinterCallback {
        void success();

        void error(String message);
    }

    private static class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

        private List<BluetoothDevice> dataList;
        private OnItemClickListener listener;

        public DeviceAdapter() {
            dataList = new ArrayList<>();
        }

        public List<BluetoothDevice> getDataList() {
            return this.dataList;
        }

        public void setDataList(List<BluetoothDevice> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_device_item, null);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 0);
            view.setLayoutParams(lp);
            return new ViewHolder(view, parent.getContext());
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(dataList.get(position), listener);
        }

        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public interface OnItemClickListener {
            void onItemClick(BluetoothDevice item);
        }

        protected static class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tvDeviceName;
            //private ImageView checkbox;
            private Context context;
            private LinearLayout layout;

            public ViewHolder(View itemView, Context context) {
                super(itemView);
                this.context = context;
                layout = itemView.findViewById(R.id.layout);
                //checkbox = itemView.findViewById(R.id.checkbox);
                tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
            }

            public void bind(final BluetoothDevice data, OnItemClickListener listener) {

                tvDeviceName.setText(data.getName());

                /*if(data.isSelected){
                    checkbox.setImageResource(R.mipmap.ic_check_circle_black_24dp);
                    checkbox.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                else {
                    checkbox.setImageResource(R.mipmap.ic_radio_button_unchecked_black_24dp);
                    checkbox.setColorFilter(ContextCompat.getColor(context, R.color.dark_grey), android.graphics.PorterDuff.Mode.SRC_IN);
                }*/


                layout.setOnClickListener(v -> {
                    listener.onItemClick(data);
                });
            }
        }

    }
}
