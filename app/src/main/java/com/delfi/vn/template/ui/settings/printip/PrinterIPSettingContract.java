package com.delfi.vn.template.ui.settings.printip;

import com.delfi.vn.template.ui.base.BaseView;

public interface PrinterIPSettingContract {
    interface View extends BaseView {
        void validateError();
        void saveChanged();
    }

    interface Presenter {
        void save(String ip, String port,String dpi);
    }
}
