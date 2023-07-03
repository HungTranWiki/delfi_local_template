package com.delfi.vn.template.ui.main;

import com.delfi.vn.template.ui.base.BaseView;
import com.delfi.vn.template.models.appmodels.MainMenu;

import java.util.List;

public interface MainContract {
    interface View extends BaseView {
        void onRefreshMenuList(List<MainMenu> mainMenuList);

        void logoutSuccess();

        void forceLogout();

        void syncDataSuccess();

        void syncDataError(String error);

        void goToSyncCommunicationAfterExported();

        void saveDataSuccess();
    }

    interface Presenter {
        void showMenuList(boolean isManager);

        void logOut();

        void setURLConfigBeforeUpdate();

        void checkURLConfigIsChanged();

        void syncData();

        boolean isActivation();

        void exportDB();

        void importDB();
    }
}
