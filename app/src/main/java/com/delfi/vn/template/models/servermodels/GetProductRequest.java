package com.delfi.vn.template.models.servermodels;

import com.google.gson.annotations.SerializedName;

public class GetProductRequest {
    @SerializedName("ma_vt")
    private String ma_vt;

    public GetProductRequest(String ma_vt) {
        this.ma_vt = ma_vt;
    }
}
