package com.delfi.vn.template.models.appmodels;

public class ProductMenu11 {
    public int Id;
    public String soCT;
    public String productId;
    public String maVT;
    public float missingQty;
    public String barcode;
    public String configId;
    public String sizeId;
    public String colorId;
    public String styleId;
    public String unit;
    public String note;
    public int status;
    public String tenVT = "";
    public float soLuongDaQuet = 0;
    public float soLuongYeuCau = 0;
    @Override
    public String toString() {
        return String.format("%s,%s", maVT, barcode);
    }

}
