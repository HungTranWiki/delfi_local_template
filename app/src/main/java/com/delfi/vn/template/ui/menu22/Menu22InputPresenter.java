package com.delfi.vn.template.ui.menu22;

import android.annotation.SuppressLint;

import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.enums.ErrorCode;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.repositories.Receipt11Repo;
import com.delfi.vn.template.ui.base.screen.BaseFeaturePresenter;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;

import java.util.List;

import javax.inject.Inject;

public class Menu22InputPresenter extends BaseFeaturePresenter<Menu22InputContract.View>
        implements Menu22InputContract.Presenter {
    private Receipt11Repo receipt11Repo;
    private List<Receipt11> receiptList;
    private Menu22InputContract.View view;

    @Inject
    public Menu22InputPresenter(
            Menu22InputContract.View view,
            SchedulerListener schedulerListener,
            Receipt11Repo receipt11Repo,
            AppRepository appRepo) {
        super(view, schedulerListener, appRepo);
        this.view = view;
        this.receipt11Repo = receipt11Repo;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initDataToStartScreen() {
        receipt11Repo.pullReceiptListFromServer()
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(disposable -> {
                    view.showLoadingDialog();
                    addToDispose(disposable);
                })
                .doFinally(view::hideLoadingDialog)
                .subscribe(response -> {
                            this.receiptList = response;
                            view.showReceiptList(receiptList);
                        },
                        throwable -> {
                            boolean result = handleException(throwable);
                            if (!result)
                                if (throwable instanceof AppException) {
                                    view.showErrorMessage((AppException) throwable);
                                } else {
                                    view.showErrorMessage(new AppException(ErrorCode.GET_RECEIPT_PO_1_ERROR));
                                }

                        }
                );
    }
}
