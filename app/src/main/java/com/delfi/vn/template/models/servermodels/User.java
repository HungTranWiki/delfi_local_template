package com.delfi.vn.template.models.servermodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("access_token")
    private String token;

    public String getToken() {
        return token;
    }
}
