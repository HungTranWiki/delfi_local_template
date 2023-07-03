package com.delfi.vn.template.utils.printer;

import android.content.Context;
import android.graphics.Typeface;

import com.bp.BP;
import com.delfi.vn.template.utils.Constants;
import com.delfi.vn.template.utils.printer.model.BxlElement;
import com.delfi.vn.template.utils.printer.model.BxlLabel;
import com.delfi.vn.template.utils.printer.model.BxlQRBarcode;
import com.delfi.vn.template.utils.printer.model.BxlText;

import java.io.IOException;

/**
 * Supports XT2-40
 */
public class BXLWifiXT2Printer extends BaseBXLPrinter {
    public BXLWifiXT2Printer(Context context) {
        super(context);
        BP.getMainContext(context);
    }

    @Override
    public boolean isConnected() {
        return mIsConnected;
    }

    @Override
    public boolean connect(String address) {
        if (!mIsConnected)
            mIsConnected = BP.openport(address, Constants.BXL_WIFI_PORT);
        return mIsConnected;
    }

    @Override
    public void setup(int width, int dark, int speed, int mode, int gap, int top) {
        BP.setup(String.valueOf(mLabelWidth - 1), String.valueOf(dark), String.valueOf(speed), String.valueOf(mode), String.valueOf(gap), String.valueOf(top));
    }

    @Override
    public void print(BxlLabel label) {
        for (BxlElement e : label.getBxlElements()) {
            if (e instanceof BxlText)
                printText(e.getPositionX(), e.getPositionY(), ((BxlText) e).getFontSize(), ((BxlText) e).getBold(), ((BxlText) e).getText());
            if (e instanceof BxlQRBarcode)
                printQRCode(e.getPositionX(), e.getPositionY(), ((BxlQRBarcode) e).getData(), e.getBarcodeSize());
        }
    }

    @Override
    public void printText(int x, int y, int size, boolean bold, String text) {
        BP.ecTextOut(x, y, Typeface.SANS_SERIF, (bold ? Typeface.BOLD : Typeface.NORMAL), size, 0,
                false, false, false, text);
    }

    @Override
    public void printQRCode(int x, int y, String data) {
        BP.Bar_QRCode(x, y, 5, 2, "M", 8, 4, 0, data);
    }

    @Override
    public void printQRCode(int x, int y, String data, int size) {
        BP.Bar_QRCode(x, y, 5, 2, "M", 8, size, 0, data);
    }

    @Override
    public void setWidth(int labelWidth) {
        super.mLabelWidth = labelWidth;
    }

    @Override
    public void setDensity(int density) {
        super.mDensity = density;
    }

    @Override
    public void setSpeed(int speed) {
        super.mSpeed = speed;
    }

    @Override
    public void setOrientation(int orientation) {
        super.mOrientation = orientation;
    }

    @Override
    public void disconnect() {
        try {
            if (mIsConnected) {
                BP.close();
                mIsConnected = false;
            }
        } catch (IOException | Error e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMargin(int left, int top) {

    }

    @Override
    public void setLength(int length, int gap, int mediaType, int offsetLength) {

    }
}
