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

public class Receipt11Detail extends BaseItem implements Serializable, IBuildDbItem {
    public int Id;

    @SerializedName("OrderId")
    public String soCT;

    @SerializedName("ProductId")
    public String productId;

    @SerializedName("DisplayProductId")
    public String maVT;

    @SerializedName("Quantity")
    public float soLuongYeuCau;

    @SerializedName("MissingQty")
    public float missingQty;

    @SerializedName("Barcode")
    public String barcode;

    @SerializedName("ConfigId")
    public String configId;

    @SerializedName("SizeId")
    public String sizeId;

    @SerializedName("ColorId")
    public String colorId;

    @SerializedName("StyleId")
    public String styleId;

    @SerializedName("Unit")
    public String unit;

    @SerializedName("Note")
    public String note;

    @SerializedName("Status")
    public int status;

    public String tenVT = "";

    public float soLuongDaQuet = 0;

    @Override
    public String buildColumnStatement() {
        return "( soCT , productId, maVT, soLuongYeuCau ,missingQty , barcode, configId, sizeId, colorId, styleId," +
                " unit, note , status, tenVT, soLuongDaQuet)";
    }

    @Override
    public String toString() {
        return String.format("%s,%s", soCT, maVT);
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
        return "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    public void bindValue(SQLiteStatement sqLiteStatement, int index) {
        index++;
        sqLiteStatement.bindString(index++, soCT);
        sqLiteStatement.bindString(index++, productId);

        sqLiteStatement.bindString(index++, maVT);
        sqLiteStatement.bindString(index++, soLuongYeuCau +"");
        sqLiteStatement.bindString(index ++, missingQty +"");
        sqLiteStatement.bindString(index ++, barcode);
        sqLiteStatement.bindString(index ++, configId);
        sqLiteStatement.bindString(index ++, sizeId);
        sqLiteStatement.bindString(index ++, colorId);
        sqLiteStatement.bindString(index ++, styleId);
        sqLiteStatement.bindString(index ++, unit);
        sqLiteStatement.bindString(index ++, note);
        sqLiteStatement.bindString(index ++, status +"");
        sqLiteStatement.bindString(index ++, tenVT);
        sqLiteStatement.bindString(index, soLuongDaQuet + "");
    }

    @Override
    public int totalColumns() {
        return 15;
    }
}
