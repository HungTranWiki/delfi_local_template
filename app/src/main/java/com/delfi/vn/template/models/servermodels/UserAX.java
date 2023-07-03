package com.delfi.vn.template.models.servermodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserAX implements Serializable {
    @SerializedName("access_token")
    private String token;

    @SerializedName("error")
    private String error;

    public UserAX(String token, String error) {
        this.token = token;
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }
}
