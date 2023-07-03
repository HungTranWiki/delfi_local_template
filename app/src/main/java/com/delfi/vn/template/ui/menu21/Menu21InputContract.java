package com.delfi.vn.template.ui.menu21;

import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.printmodes.Receipt11Label;
import com.delfi.vn.template.ui.base.screen.BaseFeatureContract;

import java.util.List;

public interface Menu21InputContract {
    interface View extends BaseFeatureContract.View {
        void showReceiptList(List<Receipt11> list);
        void startPrint(Receipt11Label item);
    }

    interface Presenter extends BaseFeatureContract.Presenter {
        void print(Receipt11 receipt11);
    }
}