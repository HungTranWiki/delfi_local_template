package com.delfi.vn.template.utils.printer.model;

/*
 * Created by DTO-BTAD on 6/10/2021.
 */
public abstract class BxlElement {
    protected int positionX;
    protected int positionY;
    protected int barcodeSize;
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setBarcodeSize(int barcodeSize) {
        this.barcodeSize = barcodeSize;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getBarcodeSize() {
        return barcodeSize;
    }

    protected abstract void getCommand();
}
