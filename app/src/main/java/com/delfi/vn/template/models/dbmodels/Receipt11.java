package com.delfi.vn.template.models.dbmodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Receipt11 implements Serializable {
    @SerializedName("OrderId")
    public String soCT;

    @SerializedName("FromWarehouse")
    public String fromWH;

    @SerializedName("ToWarehouse")
    public String toWH;

    @SerializedName("StatusId")
    public int statusId;

    @SerializedName("CreatedDateTime") //"CreatedDateTime": "2022-12-11T05:37:57",
    public String createDate;

    @SerializedName("ShipDate")
    public String shipDate;

    @SerializedName("ReceiveDate")
    public String receiveDate;

    @SerializedName("Note")
    public String note;

    @Override
    public String toString() {
        return String.format("%s", soCT);
    }

}
