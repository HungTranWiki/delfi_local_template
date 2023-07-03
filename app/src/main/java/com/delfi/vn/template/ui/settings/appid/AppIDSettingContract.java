package com.delfi.vn.template.ui.settings.appid;

import com.delfi.vn.template.ui.base.BaseView;

public interface AppIDSettingContract {
    interface View extends BaseView {
    }

    interface Presenter {
        void saveAppId(String appId);
    }
}
