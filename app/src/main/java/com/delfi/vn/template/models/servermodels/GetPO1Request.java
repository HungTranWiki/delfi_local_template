package com.delfi.vn.template.models.servermodels;

public class GetPO1Request {
    private String ma_kh;
    private String fromdate;

    public GetPO1Request(String ma_kh, String fromdate) {
        this.ma_kh = ma_kh;
        this.fromdate = fromdate;
    }
}
