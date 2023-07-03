package com.delfi.vn.template.models.servermodels.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseAXResponse<T> {

    @SerializedName("HasError")
    public boolean hasError;
    @SerializedName("Message")
    public String errorMessages;
    @SerializedName("Status")
    public String status;
    @SerializedName("ErrorMessages")

    public List<String> errorMessageList;

    public T data;

    @Override
    public String toString() {
        return "Response {" +
                ", msg='" + errorMessages.toString() + '\'' +
                ", data=" + data +
                '}';
    }
}
