package com.delfi.vn.template.models.appmodels;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("code")
    private String code;

    @SerializedName("msg")
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
