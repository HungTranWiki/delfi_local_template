package com.delfi.vn.template.models.dbmodels;

import android.content.Context;
import android.widget.EditText;

import com.delfi.core.controls.IDropdownItem;
import com.delfi.core.controls.editableview.interfaces.BaseItem;
import com.delfi.core.controls.editableview.model.Element;
import com.delfi.vn.template.MyApplication;

import java.util.HashMap;
import java.util.List;

public class Email extends BaseItem implements IDropdownItem {
    public int Id;
    public String email;

    public Email() {
        email = "";
    }

    public Email(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%s", email);
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
    public String getValueShowOnDropdown() {
        return email;
    }

    @Override
    public String[] validateValues() {
        return new String[0];
    }
}
