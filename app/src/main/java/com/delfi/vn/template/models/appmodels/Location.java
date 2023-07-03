package com.delfi.vn.template.models.appmodels;

import com.delfi.core.controls.IDropdownItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable , IDropdownItem {
    @SerializedName("ma_kho")
    public String maKho;
    @SerializedName("ma_vitri")
    public String maViTri;

    @Override
    public String toString() {
        return String.format("%s", maViTri);
    }

    public Location() {
    }

    @Override
    public String getValueShowOnDropdown() {
        return String.format("%s", maViTri);
    }

    @Override
    public String[] validateValues() {
        return new String[0];
    }
}
