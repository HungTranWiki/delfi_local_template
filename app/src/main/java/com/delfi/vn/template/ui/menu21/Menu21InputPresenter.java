package com.delfi.vn.template.ui.menu21;

import android.annotation.SuppressLint;

import com.delfi.core.log.Logger;
import com.delfi.vn.template.R;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.enums.ErrorCode;
import com.delfi.vn.template.models.printmodes.Receipt11Label;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.repositories.Receipt11Repo;
import com.delfi.vn.template.ui.base.screen.BaseFeaturePresenter;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.Constants;
import com.delfi.vn.template.utils.ObservableCall;
import com.delfi.vn.template.utils.printer.BXLPrinter;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class Menu21InputPresenter extends BaseFeaturePresenter<Menu21InputContract.View>
        implements Menu21InputContract.Presenter {
    private Receipt11Repo receipt11Repo;
    private List<Receipt11> receiptList;
    private Menu21InputContract.View view;

    @Inject
    public Menu21InputPresenter(
            Menu21InputContract.View view,
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

    @Override
    public void onCheckLocalDataForSync() {
        view.canSyncedData();
    }

    @SuppressLint("CheckResult")
    @Override
    public void print(Receipt11 receipt11) {
        onConnectToPrinter()
                .map(result -> {
                    if (result) {
                        return true;
                    }
                    throw new AppException(ErrorCode.CONNECT_PRINTER_ERROR);
                    //
                })
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(disposable -> {
                    view.showLoadingDialog();
                    addToDispose(disposable);
                })
                .doFinally(view::hideLoadingDialog)
                .subscribe(result -> {
                    Receipt11Label label = new Receipt11Label(receipt11);
                    if (label != null) {
                        view.startPrint(label);
                    }
                }, throwable -> {
                    if (throwable instanceof AppException) {
                        view.showMessage(R.string.error_printer_not_connected);
                    }
                    Logger.e("Failed to print label.", (Exception) throwable);
                    view.showMessage(throwable.getMessage());
                });
    }

    public Observable<Boolean> onConnectToPrinter() {
        if (BXLPrinter.getInstance().isConnected())
            return Observable.just(true);

        String printerAddress = appRepository.getPrinterAddress();
        String workingPrinter = appRepository.getWorkingPrinter();
        boolean isWifiPrinter = workingPrinter.equalsIgnoreCase(Constants.BXL_XT2) || workingPrinter.equalsIgnoreCase(Constants.BXL_XT5);
        if (!isWifiPrinter)
            printerAddress = appRepository.getBluetoothPrinterAddress();

        String finalPrinterAddress = printerAddress;
        return ObservableCall.observable(() ->
                {
                    return BXLPrinter.getInstance().connect(finalPrinterAddress);//Connect wifi port
                }
        ).map(connected -> connected);
    }

}
