package com.delfi.vn.template.ui.menu11.detail;

import com.delfi.vn.template.models.appmodels.ProductMenu11;
import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.ui.base.screen.BaseFeatureContract;
import com.delfi.vn.template.utils.AppException;

import java.util.List;

public interface Menu11DetailContract {
    interface View extends BaseFeatureContract.View {
        void showProductList(String soCT, List<ProductMenu11> locationList);

        void showEditQuantity(int position, ProductMenu11 product);

        void savedDataSuccess(int position, String barcode, ProductMenu11 menu11);
    }

    interface Presenter extends BaseFeatureContract.Presenter {
        void receiptData(Warehouse warehouse, Receipt11 receiptPO1);

        void setSelectedProduct(int position, ProductMenu11 receiptPO1);

        ProductMenu11 getSelectedProduct();

        void saveQuantity(int position, float soLuong, ProductMenu11 selectedProduct, String barcode);

        void refresh();
    }
}
