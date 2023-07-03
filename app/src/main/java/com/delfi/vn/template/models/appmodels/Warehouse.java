package com.delfi.vn.template.models.appmodels;

import com.delfi.core.controls.IDropdownItem;
import com.delfi.core.log.LogEventArgs;
import com.delfi.core.log.LogLevel;
import com.delfi.core.log.Logger;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Warehouse implements Serializable, IDropdownItem {
    public int Id;

    @SerializedName("WarehouseCode")
    public String maKho;

    @SerializedName("WarehouseName")
    public String tenKho;

    @SerializedName("ContactName")
    public String contactName;

    @SerializedName("ContactPhone")
    public String contactPhone;

    @SerializedName("Address")
    public String address;

    @SerializedName("AddressNo")
    public String addressNo;

    @SerializedName("Ward")
    public String ward;

    @SerializedName("District")
    public String district;

    @SerializedName("Province")
    public String province;

    public Warehouse() {
    }

    public Warehouse buildItemFromFile(String s) {
        try {
            String[] dataArray = s.trim().split("@@");
            int i = 0;
            this.maKho = dataArray[i++].trim();
            this.tenKho =  dataArray[i++].trim();
        } catch (Exception e) {
//            Logger.e(e);
//            Logger.i(BuildConfig.APPLICATION_ID , "Cannot parse from file: " + s);
            e.printStackTrace();
            Logger.getInstance().logMessage(new LogEventArgs(LogLevel.ERROR, "[Customer] Line Error Format: \n" + s, e));
            return null;
        }
        return this;
    }


    @Override
    public String toString() {
        return String.format("%s - %s", maKho, tenKho);
    }

    @Override
    public String getValueShowOnDropdown() {
        return String.format("%s", tenKho);
    }

    @Override
    public String[] validateValues() {
        return new String[0];
    }

}
