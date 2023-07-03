package com.delfi.vn.template.ui.settings.appid;

import com.delfi.vn.template.ui.base.BasePresenter;
import com.delfi.vn.template.repositories.AppRepository;

import javax.inject.Inject;

public class AppIDSettingPresenter extends BasePresenter<AppIDSettingContract.View>
        implements AppIDSettingContract.Presenter {

    private AppRepository appRepository;

    @Inject
    public AppIDSettingPresenter(AppIDSettingContract.View view,
                                 AppRepository appRepository) {
        super(view);
        this.appRepository = appRepository;
    }

    @Override
    public void saveAppId(String appId) {
        appRepository.saveAppID(appId);

    }
}
