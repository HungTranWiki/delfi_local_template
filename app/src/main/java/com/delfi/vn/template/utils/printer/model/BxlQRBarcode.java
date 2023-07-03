package com.delfi.vn.template.utils.printer.model;

import com.bp.BP;

/*
 * Created by DTO-BTAD on 6/10/2021.
 */
public class BxlQRBarcode extends BxlElement {
    private final String data;

    public BxlQRBarcode(int x, int y, String data) {
        this(x, y, data, 5);
    }

    public BxlQRBarcode(int x, int y, String data, int size) {
        super.setPositionX(x);
        super.setPositionY(y);
        super.setBarcodeSize(size);
        this.data = data;
    }

    @Override
    protected void getCommand() {
        BP.Bar_QRCode(getPositionX(), getPositionY(), 5, 2, "M", 8, 4, 0, data);
    }

    public String getData() {
        return data;
    }
}
