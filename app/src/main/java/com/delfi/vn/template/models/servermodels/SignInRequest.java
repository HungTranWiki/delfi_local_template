package com.delfi.vn.template.models.servermodels;

import com.google.gson.annotations.SerializedName;

public class SignInRequest {
    @SerializedName("username")
    private String userName;

    @SerializedName("password")
    private String password;

    public SignInRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
