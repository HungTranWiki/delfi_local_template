package com.delfi.vn.template.models.dbmodels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.widget.EditText;

import com.delfi.core.controls.editableview.interfaces.BaseItem;
import com.delfi.core.controls.editableview.model.DF_InputType;
import com.delfi.core.controls.editableview.model.Element;
import com.delfi.vn.template.MyApplication;
import com.delfi.vn.template.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SavedMenu11 extends BaseItem implements Serializable {
    public int Id;
    public String soCT;
    public String maVT;
    public String productId;
    public float missingQty;
    public String barcode;
    public String configId;
    public String sizeId;
    public String colorId;
    public String styleId;
    public String unit;
    public String note;
    public int status;
    public String tenVT;
    public float soLuongDaQuet;
    public float soLuongYeuCau;
    public String fromWH;
    public String toWH;
    public String createDate;
    public String shipDate;
    public String receiveDate;
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

    @SuppressLint("DefaultLocale")
    @Override
    public String getRecordStringOutputFile(Context context) {
        return String.format("%s,%s,%f", soCT,maVT,soLuongDaQuet);
    }

    @Override
    public String getConfirmDeleteString(Context context) {
        String s = "\n" + context.getString(R.string.order_id) + ": " + this.soCT + "\n"
                + context.getString(R.string.ma_vat_tu) + ": " + this.maVT + "\n"
                + context.getString(R.string.quantity) + ": " + this.soLuongDaQuet + "\n";
        return context.getString(R.string.confirm_delete_single, s);
    }

    @Override
    public String getConfirmMultiDeleteString(Context context, List list) {
        StringBuilder s = new StringBuilder();
        s.append("\n");
        for (Object object : list) {
            SavedMenu11 item = (SavedMenu11) object;
            s.append(context.getString(R.string.order_id)).append(": ").append(item.soCT).append("\n")
                    .append(context.getString(R.string.ma_vat_tu)).append(": ").append(item.maVT).append("\n")
                    .append(context.getString(R.string.quantity)).append(": ").append(item.soLuongDaQuet).append("\n\n");
        }
        return String.format(getAppContext().getResources().getString(R.string.confirm_delete_multi), s);
    }

    @Override
    public List<Element> buildEditItemTemplate(Context context) {

            List<Element> rows = new ArrayList<>();

            Element row0 = new Element.Builder("row0")
                    .setLabel(context.getString(R.string.quantity))
                    .setValue(String.valueOf(soLuongDaQuet))
                    .setEditable(true)
                    .setInputType(new DF_InputType(InputType.TYPE_CLASS_NUMBER, InputType.TYPE_CLASS_NUMBER))
                    .setMaxLength(10)
                    .build();

            rows.add(row0);

            return rows;

    }

    @Override
    public void onUpdateValue(Context context, HashMap<String, String> hashMap) {
    }

    @Override
    public String validateRules(Context context, EditText editText, String s) {
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s", maVT);
    }

}