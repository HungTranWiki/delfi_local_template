package com.delfi.vn.template.ui.base.screen;

import com.delfi.vn.template.models.printmodes.PrintItem;
import com.delfi.vn.template.ui.base.BaseView;
import com.delfi.vn.template.utils.AppException;

import java.util.List;

public interface BaseFeatureContract {
    interface View extends BaseView {

        void onGetDataToStartError(AppException throwable);

        void onCheckLocalDataForSync(int result);

        void canSyncedData();

        void canNotSyncedData();

        void viewList(int size);

        void onSyncDataSuccess();

        void onSyncDataError();

        void onPrintAsync(List items);

        void updateAfterPrint(PrintItem item);

        void showChoosePrinter();
    }

    interface Presenter {
        void getDataToStart();

        void onCheckLocalDataForSync();

        void onSyncData();

        void getDataToPrint();
    }
}
