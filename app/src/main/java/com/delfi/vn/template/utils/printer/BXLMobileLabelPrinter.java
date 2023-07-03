package com.delfi.vn.template.utils.printer;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.bixolon.commonlib.BXLCommonConst;
import com.bixolon.commonlib.log.LogService;
import com.bixolon.labelprinter.BixolonLabelPrinter;
import com.delfi.vn.template.utils.DialogManager;
import com.delfi.vn.template.utils.printer.model.BxlElement;
import com.delfi.vn.template.utils.printer.model.BxlLabel;
import com.delfi.vn.template.utils.printer.model.BxlQRBarcode;
import com.delfi.vn.template.utils.printer.model.BxlText;

import java.util.Set;

/**
 * Supports SPP-L310
 */
public class BXLMobileLabelPrinter extends BaseBXLPrinter {
    static BixolonLabelPrinter printer;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BixolonLabelPrinter.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BixolonLabelPrinter.STATE_CONNECTED:
                            mIsConnected = true;
                            Log.d("BT_PRINTER", "Connected");
                            break;
                        case BixolonLabelPrinter.STATE_NONE:
                            mIsConnected = false;
                            Log.d("BT_PRINTER", "Disconnected");
                            break;
                    }
                    break;
                case BixolonLabelPrinter.MESSAGE_DEVICE_NAME:
                    String mConnectedDeviceName = msg.getData().getString(BixolonLabelPrinter.DEVICE_NAME);
                    Toast.makeText(context, mConnectedDeviceName, Toast.LENGTH_LONG).show();
                    break;

                case BixolonLabelPrinter.MESSAGE_BLUETOOTH_DEVICE_SET:
                    if (msg.obj == null) {
                        Toast.makeText(context, "No paired device", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogManager.showBluetoothDialog(context, (Set<BluetoothDevice>) msg.obj);
                    }
                    break;
            }
            return false;
        }
    });

    public BXLMobileLabelPrinter(Context context) {
        super(context);
        printer = new BixolonLabelPrinter(context, mHandler, Looper.getMainLooper());
        LogService.InitDebugLog(true, true, BXLCommonConst._LOG_LEVEL_HIGH);
    }

    @Override
    public boolean isConnected() {
        return super.mIsConnected;
    }

    @Override
    public boolean connect(String address) {
        if (!mIsConnected)
            printer.connect(address);
        return true;
    }

    @Override
    public void setup(int width, int dark, int speed, int mode, int gap, int top) {
        printer.initializePrinter();
        setWidth(width);
        setMargin(10, 10);
    }

    @Override
    public void print(BxlLabel label) {
        printer.beginTransactionPrint();
        for (BxlElement e : label.getBxlElements()) {
            if (e instanceof BxlText)
                printText(e.getPositionX(), e.getPositionY(), ((BxlText) e).getFontSize(), ((BxlText) e).getBold(), ((BxlText) e).getText());
            if (e instanceof BxlQRBarcode)
                printQRCode(e.getPositionX(), e.getPositionY(), ((BxlQRBarcode) e).getData());
        }
        printer.print(1, 1);
        printer.endTransactionPrint();
        printer.clearBuffer();
    }

    @Override
    public void printText(int x, int y, int size, boolean bold, String text) {
        printer.drawText(text, x, y, size, 1, 1, 0, BixolonLabelPrinter.ROTATION_NONE, false, bold, BixolonLabelPrinter.TEXT_ALIGNMENT_LEFT);
    }

    @Override
    public void printQRCode(int x, int y, String data) {
        printer.drawQrCode(data, x, y, BixolonLabelPrinter.QR_CODE_MODEL1, BixolonLabelPrinter.ECC_LEVEL_15, 5, BixolonLabelPrinter.ROTATION_NONE);
    }

    @Override
    public void printQRCode(int x, int y, String data, int size) {
        printer.drawQrCode(data, x, y, BixolonLabelPrinter.QR_CODE_MODEL1, BixolonLabelPrinter.ECC_LEVEL_15, size, BixolonLabelPrinter.ROTATION_NONE);
    }

    @Override
    public void setWidth(int labelWidth) {
        printer.setWidth(labelWidth);
    }

    @Override
    public void setLength(int length, int gap, int mediaType, int offsetLength) {
        printer.setLength(length, gap, mediaType, offsetLength);
    }

    @Override
    public void setDensity(int density) {
        printer.setDensity(density);
    }

    @Override
    public void setSpeed(int speed) {
        printer.setSpeed(speed);
    }

    @Override
    public void setOrientation(int orientation) {
        printer.setOrientation(orientation);
    }

    @Override
    public void disconnect() {
        try {
            if (mIsConnected) {
                printer.disconnect();
                mIsConnected = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMargin(int left, int top) {
        printer.setMargin(left, top);
    }
}
