package com.delfi.vn.template.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.delfi.core.controls.editableview.ui.ViewListRecordActivity;

public abstract class BaseRecordsActivity extends ViewListRecordActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
