package com.delfi.vn.template.ui.settings.printbluetooth;


import com.delfi.vn.template.ui.base.BaseView;

public interface PrinterSettingContract {
    interface View extends BaseView {
        void onPrinterConnected(boolean status);
    }

    interface Presenter {
        void save(String address);
        void connect(String address);
    }
}
