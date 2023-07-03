package com.delfi.vn.template.ui.menu11.input;

import android.annotation.SuppressLint;
import android.util.Log;

import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.enums.ErrorCode;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.repositories.GeneralRepo;
import com.delfi.vn.template.repositories.Menu11Repo;
import com.delfi.vn.template.repositories.Receipt11Repo;
import com.delfi.vn.template.ui.base.screen.BaseFeaturePresenter;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class Menu11InputPresenter extends BaseFeaturePresenter<Menu11InputContract.View>
        implements Menu11InputContract.Presenter {
    private GeneralRepo generalRepo;
    private Receipt11Repo receipt11Repo;
    private List<Warehouse> warehouseList;

    private List<Receipt11> receiptList;
    private Receipt11 selectedReceipt = null;
    private Warehouse selectedWarehouse = null;

    private Menu11InputContract.View view;

    @Inject
    public Menu11InputPresenter(
            Menu11InputContract.View view,
            SchedulerListener schedulerListener,
            GeneralRepo generalRepo,
            Menu11Repo menu11Repo,
            Receipt11Repo receipt11Repo,
            AppRepository appRepo) {
        super(view, schedulerListener, appRepo);
        this.view = view;
        this.generalRepo = generalRepo;
        this.receipt11Repo = receipt11Repo;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initDataToStartScreen() {
        generalRepo.getWarehouseList()
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(disposable -> {
                    view.showLoadingDialog();
                    addToDispose(disposable);
                })
                .doFinally(view::hideLoadingDialog)
                .subscribe(response -> {
                            this.warehouseList = response;
                            view.showWarehouseList(warehouseList);
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

    @SuppressLint("CheckResult")
    @Override
    public Warehouse findWarehouseByKey(String key) {
        for (Warehouse warehouse : warehouseList) {
            if(warehouse.maKho == null || warehouse.tenKho == null || key == null){
                continue;
            }
            if (warehouse.maKho.equalsIgnoreCase(key) || warehouse.tenKho.equalsIgnoreCase(key)
                    || warehouse.toString().equalsIgnoreCase(key)) {
                this.selectedWarehouse = warehouse;
                return warehouse;
            }
        }
        this.selectedWarehouse = null;
        return null;
    }

    @SuppressLint("CheckResult")
    @Override
    public void pullReceiptsFromServer() {
        if(selectedWarehouse == null)
            return;
        receipt11Repo.pullReceiptListFromServer(selectedWarehouse.maKho)
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(disposable -> {
                    view.showLoadingDialog();
                    addToDispose(disposable);
                })
                .doFinally(view::hideLoadingDialog)
                .subscribe(new Consumer<List<Receipt11>>() {
                               @Override
                               public void accept(List<Receipt11> response) throws Exception {
                                   Menu11InputPresenter.this.receiptList = response;
                                   view.showReceiptList(receiptList);
                               }
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


    @SuppressLint("CheckResult")
    @Override
    public void setSelectedReceipt(Receipt11 Receipt11) {
        this.selectedReceipt = Receipt11;
        view.showProductsScreen(selectedWarehouse, this.selectedReceipt);
    }

    @Override
    public void resetReceiptList() {
        this.selectedReceipt = null;
        this.receiptList = null;
    }
}
