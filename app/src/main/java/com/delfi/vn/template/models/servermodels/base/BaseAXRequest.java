package com.delfi.vn.template.models.servermodels.base;

import com.google.gson.annotations.SerializedName;

public class BaseAXRequest {
    @SerializedName("token")
    private String token;

    @SerializedName("memvars")
    private Object[] memVars;

    public BaseAXRequest(String token, Object[] memVars) {
        this.token = token;
        this.memVars = memVars;
    }
}
