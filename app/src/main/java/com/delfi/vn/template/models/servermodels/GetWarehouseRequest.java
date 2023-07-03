package com.delfi.vn.template.models.servermodels;

import com.google.gson.annotations.SerializedName;

public class GetWarehouseRequest {
    @SerializedName("ma_kho")
    private String maKho;

    public GetWarehouseRequest(String maKho) {
        this.maKho = maKho;
    }
}
