package com.delfi.vn.template.ui.base;

import android.os.Bundle;

import com.delfi.core.controls.editableview.interfaces.BaseItem;
import com.delfi.core.controls.editableview.ui.EditRecordActivity;

public class BaseEditRecordActivity extends EditRecordActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDoneEdit(BaseItem item) {
        super.onDoneEdit(item);
    }
}
