package com.delfi.vn.template.ui.login;

import com.delfi.vn.template.ui.base.BaseView;
import com.delfi.vn.template.utils.AppException;

public interface LoginContract {
    interface View extends BaseView {
        void onLoginSuccess();

        void onLoginFail(AppException throwable);
    }

    interface Presenter {
        void onLogin(String deviceId, String username, String password);

        String getLastedUsername();

        String getLastedPassword();
    }
}
