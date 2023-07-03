package com.delfi.vn.template.utils.printer;

import android.content.Context;

import com.delfi.vn.template.utils.printer.model.BxlLabel;

public abstract class BaseBXLPrinter {
    protected  boolean mIsConnected;
    protected  Context context;
    protected int mLabelWidth;
    protected int mDensity;
    protected int mSpeed;
    protected int mOrientation;
    public BaseBXLPrinter(Context context){
        this.context=context;
    }
    public abstract boolean isConnected();
    public abstract boolean connect(String address);
    public abstract void setup(int width,int dark, int speed, int mode, int gap, int top);
    public abstract void print(BxlLabel label);
    public abstract void printText(int x, int y, int size, boolean bold, String text);
    public abstract void printQRCode(int x, int y, String data);

    public abstract void printQRCode(int x, int y, String data, int size);

    public abstract void setWidth(int labelWidth);
    public abstract void setLength(int length, int gap, int mediaType, int offsetLength);
    public abstract void setDensity(int density);
    public abstract void setSpeed(int speed);
    public abstract void setOrientation(int orientation);
    public abstract void disconnect();
    public abstract void setMargin(int left, int top);
}
