package com.delfi.vn.template.ui.menu11.input;

import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.ui.base.screen.BaseFeatureContract;

import java.util.List;

public interface Menu11InputContract {
    interface View extends BaseFeatureContract.View {
        void showReceiptList(List<Receipt11> list);

        void showProductsScreen(Warehouse warehouse, Receipt11 Receipt11);

        void showWarehouseList(List<Warehouse> list);
    }

    interface Presenter extends BaseFeatureContract.Presenter {
        Warehouse findWarehouseByKey(String key);

        void pullReceiptsFromServer();

        void setSelectedReceipt(Receipt11 Receipt11);

        void resetReceiptList();
    }
}