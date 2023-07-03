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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.delfi.vn.template.R;
import com.delfi.vn.template.utils.printer.utils.BTDeviceAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/*
 * Created by DTO-BTAD on 9/15/2021.
 */
public abstract class BTSocketConnection {
    private Context context;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice device;
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
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            if (msg.what == 0) {
                Toast.makeText(context, "Đã gửi dữ liệu đến máy in.", Toast.LENGTH_SHORT).show();
                sendPrintJob();
            } else {
                errorCallBack("Không thể kết nối đến thiết bị!\nBạn có muốn tiếp tục?");
            }
        }
    };
    public BTSocketConnection(Context context) {
        this.context = context;
    }

    protected void enableBluetooth() {
        Toast.makeText(context, "enableBluetooth", Toast.LENGTH_SHORT).show();
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            boolean isEnabled = bluetoothAdapter.isEnabled();
            if (!isEnabled) {
                IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
                this.context.registerReceiver(mReceiver, filter);
                bluetoothAdapter.enable();
            } else {
                Toast.makeText(context, "initPrinter", Toast.LENGTH_SHORT).show();
                initPrinter(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unregisterReceiver() {
        try {
            this.context.unregisterReceiver(mReceiver);
        } catch (Exception e) {
        }
    }

    protected void initPrinter(boolean showChoose) {
        unregisterReceiver();
        if (getDevice() == null) {
            Toast.makeText(context, "Get paired device", Toast.LENGTH_SHORT).show();
            Set<BluetoothDevice> pairedDevices = getBluetoothAdapter().getBondedDevices();
            if (pairedDevices.size() > 0) {
                Toast.makeText(context, "Paired device: "+pairedDevices.size(), Toast.LENGTH_SHORT).show();
                List<BluetoothDevice> items = new ArrayList<>();
                for (BluetoothDevice device : pairedDevices)
                    items.add(device);
                if (items.size() > 1 || showChoose)
                    showDialogChooseDevice(items);
                else
                    onConnectDevice(items.get(0));
            } else {
                errorCallBack("Không có thiết bị nào được tìm thấy!\nBạn có muốn tiếp tục?");
                return;
            }
        } else {
            Toast.makeText(context, "Device already connected", Toast.LENGTH_SHORT).show();
            onConnectDevice(getDevice());
        }
    }

    private void showDialogChooseDevice(List<BluetoothDevice> items) {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(View.inflate(getContext(), R.layout.dialog_choose_device, null));
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BTDeviceAdapter adapter = new BTDeviceAdapter();
        adapter.setDataList(items);
        adapter.setListener(item -> {
            dialog.dismiss();
            onConnectDevice(item);
        });
        recyclerView.setAdapter(adapter);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        dialog.show();
    }

    private void onConnectDevice(BluetoothDevice mBluetoothDevice) {
        Toast.makeText(context, "onConnectDevice: "+mBluetoothDevice.getName(), Toast.LENGTH_SHORT).show();
        UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        mBluetoothConnectProgressDialog = ProgressDialog.show(this.context,
                "Connecting...", mBluetoothDevice.getName() + " : "
                        + mBluetoothDevice.getAddress(), true, false);
        new Thread(() -> {
            try {
                mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
                bluetoothAdapter.cancelDiscovery();
                mBluetoothSocket.connect();
                mHandler.sendEmptyMessage(0);
                this.device = mBluetoothDevice;
                onConnected(mBluetoothDevice.getName());
            } catch (IOException eConnectException) {
                Log.d("connectDevice", "CouldNotConnectToSocket", eConnectException);
                closeSocket();
                mHandler.sendEmptyMessage(1);
                return;
            }
        }).start();
    }

    protected void closeSocket() {
        try {
            if(mBluetoothSocket!=null)
                mBluetoothSocket.close();
            Log.d("closeSocket", "SocketClosed");
            onDisconnected();
        } catch (IOException ex) {
            Log.d("closeSocket", "CouldNotCloseSocket");
        }
    }

    private void showConfirmDevice(BluetoothDevice item) {
        Dialog dialog = new Dialog(this.context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(View.inflate(this.context, R.layout.dialog_confirm_printer, null));
        TextView tvDeviceName = dialog.findViewById(R.id.tvDeviceName);
        Button buttonChange = dialog.findViewById(R.id.buttonChange);
        Button buttonPrint = dialog.findViewById(R.id.buttonPrint);
        tvDeviceName.setText(item.getName());
        buttonChange.setOnClickListener(view -> {
            device = null;
            closeSocket();
            initPrinter(true);
            dialog.dismiss();
        });
        buttonPrint.setOnClickListener(view -> {
            sendPrintJob();
            dialog.dismiss();
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.show();
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public Context getContext() {
        return context;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public BluetoothSocket getSocket() {
        return mBluetoothSocket;
    }

    protected abstract void errorCallBack(String s);
    protected abstract void onDisconnected();
    protected abstract void onConnected(String deviceName);
    protected abstract void sendPrintJob();
}
