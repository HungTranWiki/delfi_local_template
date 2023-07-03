package com.delfi.vn.template.ui.login;

import android.annotation.SuppressLint;
import android.util.Log;

import com.delfi.vn.template.ui.base.BasePresenter;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.repositories.UserRepository;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;

import javax.inject.Inject;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    private SchedulerListener mSchedulerListener;

    private UserRepository userRepository;
    private AppRepository appRepository;

    @Inject
    public LoginPresenter(LoginContract.View view,
                          SchedulerListener schedulerListener,
                          UserRepository repository,
                          AppRepository appRepository) {
        super(view);
        this.mSchedulerListener = schedulerListener;
        this.userRepository = repository;
        this.appRepository = appRepository;
    }

    @SuppressLint("CheckResult")
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onLogin(String deviceId, String username, String password) {
        Log.d("Tag: ", "onLogin()");
        view.onLoginSuccess();
//        userRepository.login(deviceId, "", username, password)
//                .subscribeOn(mSchedulerListener.io())
//                .observeOn(mSchedulerListener.ui())
//                .doOnSubscribe(disposable -> {
//                    view.showLoadingDialog();
//                    addToDispose(disposable);
//                })
//                .doFinally(view::hideLoadingDialog)
//                .subscribe(() -> {
//                    view.onLoginSuccess();
//                }, throwable -> {
//                    if( throwable instanceof AppException )
//                        view.onLoginFail((AppException) throwable);
//                    else
//                        view.onLoginFail(new AppException(ErrorCode.LOGIN_FAIL));
//                });
    }

    @Override
    public String getLastedUsername() {
        return appRepository.getUsername();
    }

    @Override
    public String getLastedPassword() {
        return appRepository.getPassword();
    }
}