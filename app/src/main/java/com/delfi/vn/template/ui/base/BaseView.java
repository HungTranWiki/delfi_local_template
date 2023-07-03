package com.delfi.vn.template.ui.base;

import android.support.annotation.StringRes;

import com.delfi.vn.template.utils.AppDialog;
import com.delfi.vn.template.utils.AppException;

public interface BaseView {
    void showLoadingDialog();

    void hideLoadingDialog();

    void showErrorMessage(AppException exception);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void showError(int errCode);
}
