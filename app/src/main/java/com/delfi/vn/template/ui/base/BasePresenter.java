package com.delfi.vn.template.ui.base;

import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.Constants;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends BaseView> {
    protected final V view;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected BasePresenter(V view) {
        this.view = view;
    }

    public void addToDispose(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void clearDispose() {
        compositeDisposable.dispose();
    }

    protected boolean handleException(Throwable e) {
        boolean isError = false;
        if (e instanceof AppException) {
            isError = ((AppException) e).getStatusCode() == Constants.HTTP_STATUS_INTERNAL_SERVER_ERROR
                    || ((AppException) e).getStatusCode() == Constants.HTTP_STATUS_UNAUTHORIZED;
            view.showError(((AppException) e).getStatusCode());
        }
        return isError;
    }
}
