package com.delfi.vn.template.models.servermodels;

import com.google.gson.annotations.SerializedName;

public class GetLocationRequest {
    @SerializedName("ma_kho")
    private String maKho;

    public GetLocationRequest(String maKho) {
        this.maKho = maKho;
    }
}
