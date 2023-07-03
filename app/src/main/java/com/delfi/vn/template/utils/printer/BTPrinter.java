package com.delfi.vn.template.utils.printer;

import android.content.Context;
import android.widget.Toast;

import com.delfi.vn.template.utils.printer.utils.ESCCommands;
import com.google.common.primitives.Bytes;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BTPrinter extends BTSocketConnection {
    private IPrintCallback printerCallback;
    private IBTConnectionCallback connectionCallback;
    private int printLoop = 1;
    private byte[] printData;

    public BTPrinter(Context context) {
        super(context);
    }


    public void setPrinterCallback(IPrintCallback printerCallback) {
        this.printerCallback = printerCallback;
    }

    public void setConnectionCallback(IBTConnectionCallback connectionCallback) {
        this.connectionCallback = connectionCallback;
    }

    public void setPrintLoop(int printLoop) {
        this.printLoop = printLoop;
    }

    public byte[] getPrintData() {
        return printData;
    }

    public void setPrintData(byte[] printData) {
        this.printData = printData;
    }

    public void send(byte[] data, IPrintCallback printerCallback) {
        send(data, 1, printerCallback);
    }

    public void send(byte[] data, int loop, IPrintCallback printerCallback) {
        this.printLoop = loop;
        setPrintData(data);
        setPrinterCallback(printerCallback);
        enableBluetooth();
    }

    @Override
    protected void errorCallBack(String s) {
        if (printerCallback != null)
            printerCallback.error(s);
    }

    @Override
    protected void onDisconnected() {
        if (connectionCallback != null)
            connectionCallback.onDisconnected();
    }

    @Override
    protected void onConnected(String deviceName) {
        if (connectionCallback != null)
            connectionCallback.onConnected(deviceName);
    }

    @Override
    protected void sendPrintJob() {
        Toast.makeText(getContext(), "sendPrintJob", Toast.LENGTH_SHORT).show();
        try {
            Thread t = new Thread() {
                public void run() {
                    try {
                        List<Byte> bytes = new ArrayList<>();
                        OutputStream os = getSocket().getOutputStream();
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
                        closeSocket();
                        try {
                            printerCallback.success();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        closeSocket();
                        printerCallback.error(e.getMessage());
                    }
                }
            };
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
            printerCallback.error(e.getMessage());
        }
    }

    public interface IPrintCallback {
        void success();

        void error(String message);
    }

    public interface IBTConnectionCallback {
        void onDisconnected();

        void onConnected(String deviceName);
    }
}
