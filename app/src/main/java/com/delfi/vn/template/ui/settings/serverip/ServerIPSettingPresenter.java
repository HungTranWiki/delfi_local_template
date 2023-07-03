package com.delfi.vn.template.ui.settings.serverip;

import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.services.api.ApiRepositoryImpl;
import com.delfi.vn.template.ui.base.BasePresenter;

import java.net.URL;

import javax.inject.Inject;

public class ServerIPSettingPresenter extends BasePresenter<ServerIPSettingContract.View>
        implements ServerIPSettingContract.Presenter {

    private ApiRepositoryImpl apiRepositoryImpl;
    private AppRepository appRepository;

    @Inject
    public ServerIPSettingPresenter(ServerIPSettingContract.View view,
                                    ApiRepositoryImpl apiRepositoryImpl,
                                    AppRepository appRepository) {
        super(view);
        this.apiRepositoryImpl = apiRepositoryImpl;
        this.appRepository = appRepository;
    }

    @Override
    public void validateServerId(String address) {
        if (address.length() == 0) {
            view.validateServerIdError();
            return;
        }
        if (address.charAt(address.length() - 1) != '/') {
            address += '/';
        }
        try {
            URL url = new URL(address);
            if (!(url.getProtocol().equals("https") || url.getProtocol().equals("http"))) {
                address = "http://" + address;
            }
        } catch (Exception var3) {
            address = "http://" + address;
        } finally {
            if (!appRepository.getURL().equals(address)) {
                appRepository.saveURL(address);
                view.serverIdIsChanged(true);
            } else {
                view.serverIdIsChanged(false);
            }
        }
    }
}
