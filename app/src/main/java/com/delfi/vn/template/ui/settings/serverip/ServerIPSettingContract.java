package com.delfi.vn.template.ui.settings.serverip;

import com.delfi.vn.template.ui.base.BaseView;

public interface ServerIPSettingContract {
    interface View extends BaseView {
        void serverIdIsChanged(boolean isValid);

        void validateServerIdError();
    }

    interface Presenter {
        void validateServerId(String url);
    }
}
