package com.delfi.vn.template.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnection {
    private static final String TAG="BT_PRINTER";
    private static BluetoothConnection printBlueToothHelper;
    private Activity context;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice device;
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    private IConnectionCallback printerCallback;
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
                        initPrinter();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        break;
                }
            }
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            if (msg.what == 0) {
                printerCallback.onDeviceSelected(device);
                Toast.makeText(context, "Thiết bị đã được kết nối!", Toast.LENGTH_SHORT).show();
            } else {
                printerCallback.error("Không thể kết nối đến thiết bị!\nBạn có muốn tiếp tục?");
            }
        }
    };

    private BluetoothConnection() {
    }

    public static BluetoothConnection getInstance() {
        if (printBlueToothHelper == null)
            printBlueToothHelper = new BluetoothConnection();
        return printBlueToothHelper;
    }

    public void prepare(Activity context, IConnectionCallback printerCallback) {
        this.context = context;
        this.printerCallback = printerCallback;
        enableBluetooth();
    }

    private void enableBluetooth() {
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            boolean isEnabled = bluetoothAdapter.isEnabled();
            if (!isEnabled) {
                IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                this.context.registerReceiver(mReceiver, filter);

                if (Build.VERSION.SDK_INT >= 30) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    this.context.startActivityForResult(enableBtIntent, 400);
                } else {
                    bluetoothAdapter.enable();
                }
            } else {
                initPrinter();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPrinter() {
        unregisterReceiver();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            List<BluetoothDevice> items = new ArrayList<>(pairedDevices);
            showDialogChooseDevice(items);
        } else {
            printerCallback.onNoPairedDevice("Không có thiết bị nào được tìm thấy!\nVui lòng ghép đôi thiết bị để tiếp tục?");
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
        adapter.setListener(new DeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BluetoothDevice item) {
                dialog.dismiss();
                BluetoothConnection.this.connectDevice(item);
            }
        });
        recyclerView.setAdapter(adapter);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        dialog.show();
    }

    private void connectDevice(BluetoothDevice mBluetoothDevice) {
        UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        mBluetoothConnectProgressDialog = ProgressDialog.show(this.context,
                "Connecting...", mBluetoothDevice.getName() + " : "
                        + mBluetoothDevice.getAddress(), true, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "MAC: " + mBluetoothDevice.getAddress());
                    mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
                    bluetoothAdapter.cancelDiscovery();
                    mBluetoothSocket.connect();
                    mHandler.sendEmptyMessage(0);
                    BluetoothConnection.this.device = mBluetoothDevice;
                    BluetoothConnection.this.closeSocket(mBluetoothSocket);
                } catch (IOException eConnectException) {
                    Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
                    BluetoothConnection.this.closeSocket(mBluetoothSocket);
                    mHandler.sendEmptyMessage(1);
                    return;
                }
            }
        }).start();
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    private void unregisterReceiver() {
        try {
            this.context.unregisterReceiver(mReceiver);
        } catch (Exception e) {
        }
    }

    public interface IConnectionCallback {
        void onDeviceSelected(BluetoothDevice device);

        void onNoPairedDevice(String message);

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
