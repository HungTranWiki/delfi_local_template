package com.delfi.vn.template.ui.menu22;

import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.ui.base.screen.BaseFeatureContract;

import java.util.List;

public interface Menu22InputContract {
    interface View extends BaseFeatureContract.View {
        void showReceiptList(List<Receipt11> list);
    }

    interface Presenter extends BaseFeatureContract.Presenter {
    }
}