package com.delfi.vn.template.ui.settings.printbluetooth;

import android.annotation.SuppressLint;

import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.ui.base.BasePresenter;
import com.delfi.vn.template.utils.ObservableCall;
import com.delfi.vn.template.utils.printer.BXLPrinter;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;

import javax.inject.Inject;

public class PrinterSettingPresenter extends BasePresenter<PrinterSettingContract.View>
        implements PrinterSettingContract.Presenter {
    private SchedulerListener schedulerListener;
    private AppRepository appRepository;

    @Inject
    public PrinterSettingPresenter(PrinterSettingContract.View view,
                                   SchedulerListener schedulerListener,
                                   AppRepository appRepository) {
        super(view);
        this.schedulerListener = schedulerListener;
        this.appRepository = appRepository;
    }

    @Override
    public void save(String address) {
        appRepository.savePrinterAddress(address);
    }

    @SuppressLint("CheckResult")
    @Override
    public void connect(String address) {
        ObservableCall.observable(() -> BXLPrinter.getInstance().connect(address))
                .subscribeOn(schedulerListener.io())
                .observeOn(schedulerListener.ui())
                .doOnSubscribe(disposable -> {
                    view.showLoadingDialog();
                    addToDispose(disposable);
                })
                .doFinally(view::hideLoadingDialog)
                .subscribe(result -> {
                    view.onPrinterConnected(result);
                }, throwable -> {
                    view.showMessage(throwable.getMessage());
                });
    }
}
