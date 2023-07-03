package com.delfi.vn.template.models.dbmodels;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;
import android.widget.EditText;

import com.delfi.core.controls.editableview.interfaces.BaseItem;
import com.delfi.core.controls.editableview.model.Element;
import com.delfi.core.sqlite.IBuildDbItem;
import com.delfi.vn.template.MyApplication;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Product extends BaseItem implements Serializable, IBuildDbItem {
    public int Id;
    @SerializedName("ItemId")
    public String productId = "";
    @SerializedName("DisplayProductId")
    public String displayProductId = "";
    @SerializedName("ProductId")
    public String itemId = "";
    @SerializedName("ConfigId")
    public String configId = "";
    @SerializedName("SizeId")
    public String sizeId = "";
    @SerializedName("ColorId")
    public String colorId = "";
    @SerializedName("StyleId")
    public String styleId = "";
    @SerializedName("Barcode")
    public String barcode = "";
    @SerializedName("UnitCode")
    public String unitCode = "";
    @SerializedName("ProductName")
    public String productName = "";

    @SerializedName("ProductNameVI")
    public String productNameVI = "";
    @SerializedName("ProductNameEN")
    public String productNameEN = "";
    @SerializedName("Brand")
    public String brand = "";
    @SerializedName("Theme")
    public String theme = "";
    @SerializedName("RetailPrice")
    public float retailPrice = 0.0F;
    @SerializedName("UnitPerCarton")
    public float unitPerCarton = 0.0F;
    @SerializedName("UnitDepth")
    public float unitDepth = 0.0F;
    @SerializedName("UnitHeight")
    public float unitHeight = 0.0F;
    @SerializedName("UnitWeight")
    public float unitWeight = 0.0F;
    @SerializedName("UnitWidth")
    public float unitWidth = 0.0F;

    @SerializedName("UnitCBM")
    public float unitCBM = 0.0F;
    @SerializedName("BoxDepth")
    public float boxDepth = 0.0F;
    @SerializedName("BoxHeight")
    public float boxHeight = 0.0F;
    @SerializedName("BoxWeight")
    public float boxWeight = 0.0F;
    @SerializedName("BoxWidth")
    public float boxWidth = 0.0F;
    @SerializedName("CartonDepth")
    public float cartonDepth = 0.0F;
    @SerializedName("CartonHeight")
    public float cartonHeight = 0.0F;
    @SerializedName("CartonWeight")
    public float cartonWeight = 0.0F;
    @SerializedName("CartonWidth")
    public float cartonWidth = 0.0F;

    @Override
    public String toString() {
        return String.format("%s,%s", barcode, displayProductId);
    }

    @Override
    public String validateRules_Async(Context context, EditText editText) {
        return null;
    }

    @Override
    public int getId() {
        return Id;
    }

    @Override
    public Context getAppContext() {
        return MyApplication.getContext();
    }

    @Override
    public String getRecordStringOutputFile(Context context) {
        return this.toString();
    }

    @Override
    public String getConfirmDeleteString(Context context) {
        return null;
    }

    @Override
    public String getConfirmMultiDeleteString(Context context, List list) {
        return null;
    }

    @Override
    public List<Element> buildEditItemTemplate(Context context) {
        return null;
    }

    @Override
    public void onUpdateValue(Context context, HashMap<String, String> hashMap) {

    }

    @Override
    public String validateRules(Context context, EditText editText, String s) {
        return null;
    }

    @Override
    public String buildParamStatement() {
        return "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    public void bindValue(SQLiteStatement sqLiteStatement, int index) {
        index++;
        sqLiteStatement.bindString(index++, productId == null ? "": productId);
        sqLiteStatement.bindString(index++, displayProductId== null ? "": displayProductId);
        sqLiteStatement.bindString(index++, itemId== null ? "": itemId);
        sqLiteStatement.bindString(index++, configId== null ? "": configId);
        sqLiteStatement.bindString(index++, sizeId== null ? "": sizeId);
        sqLiteStatement.bindString(index++, colorId== null ? "": colorId);
        sqLiteStatement.bindString(index++, styleId== null ? "": styleId);
        sqLiteStatement.bindString(index++, barcode == null ? "": barcode);
        sqLiteStatement.bindString(index++, unitCode== null ? "": unitCode);
        sqLiteStatement.bindString(index++, productName== null ? "": productName);

        sqLiteStatement.bindString(index++, productNameVI== null ? "": productNameVI);
        sqLiteStatement.bindString(index++, productNameEN== null ? "": productNameEN);
        sqLiteStatement.bindString(index++, brand== null ? "": brand);
        sqLiteStatement.bindString(index++, theme== null ? "": theme);
        sqLiteStatement.bindString(index++, String.valueOf(retailPrice ));
        sqLiteStatement.bindString(index++, String.valueOf(unitPerCarton));
        sqLiteStatement.bindString(index++, String.valueOf(unitDepth));
        sqLiteStatement.bindString(index++, String.valueOf(unitHeight));
        sqLiteStatement.bindString(index++, String.valueOf(unitWeight));
        sqLiteStatement.bindString(index++, String.valueOf(unitWidth));

        sqLiteStatement.bindString(index++, String.valueOf(unitCBM));
        sqLiteStatement.bindString(index++, String.valueOf(boxDepth));
        sqLiteStatement.bindString(index++, String.valueOf(boxHeight));
        sqLiteStatement.bindString(index++, String.valueOf(boxWeight));
        sqLiteStatement.bindString(index++, String.valueOf(boxWidth));
        sqLiteStatement.bindString(index++, String.valueOf(cartonDepth));
        sqLiteStatement.bindString(index++, String.valueOf(cartonHeight));
        sqLiteStatement.bindString(index++, String.valueOf(cartonWeight));
        sqLiteStatement.bindString(index, String.valueOf(cartonWidth));

    }

    @Override
    public String buildColumnStatement() {
        return "( productId,displayProductId,itemId, configId, sizeId, colorId, styleId, barcode, unitCode, productName," +
                " productNameVI, productNameEN, brand, theme, retailPrice, unitPerCarton, unitDepth,unitHeight, unitWeight, unitWidth," +
                " unitCBM, boxDepth, boxHeight, boxWeight, boxWidth, cartonDepth, cartonHeight, cartonWeight, cartonWidth)";
    }

    @Override
    public int totalColumns() {
        return 29;
    }
}
