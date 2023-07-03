package com.delfi.vn.template.models.servermodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PrintWaybillResponse implements Serializable {
    @SerializedName("success")
    public boolean isSuccess;
}
