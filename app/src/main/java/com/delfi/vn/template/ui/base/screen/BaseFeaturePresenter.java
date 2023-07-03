package com.delfi.vn.template.ui.base.screen;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;

import com.delfi.core.controls.editableview.interfaces.IItem;
import com.delfi.vn.template.models.printmodes.PrintItem;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.ui.base.BasePresenter;
import com.delfi.vn.template.utils.Constants;
import com.delfi.vn.template.utils.ObservableCall;
import com.delfi.vn.template.utils.printer.BXLPrinter;
import com.delfi.vn.template.utils.printer.constant.BxlPPP;
import com.delfi.vn.template.utils.printer.model.BxlPrintOptions;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;

import java.util.List;

import io.reactivex.Observable;

public abstract class BaseFeaturePresenter<V extends BaseFeatureContract.View>
        extends BasePresenter<BaseFeatureContract.View>
        implements BaseFeatureContract.Presenter {
    protected final V view;
    protected SchedulerListener schedulerListener;
    protected AppRepository appRepository;
    private int activeMenu;

    protected BaseFeaturePresenter(V view, SchedulerListener schedulerListener, AppRepository appRepository) {
        super(view);
        this.schedulerListener = schedulerListener;
        this.appRepository = appRepository;
        this.view = view;
    }

    public abstract void initDataToStartScreen();

    @Override
    public void getDataToStart() {
        initDataToStartScreen();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCheckLocalDataForSync() {
        view.canNotSyncedData();
    }

    @Override
    public void onSyncData() {

    }

    public void setActiveMenu(int activeMenu) {
        this.activeMenu = activeMenu;
        String def = getDefaultPrinter();
        if (!TextUtils.isEmpty(def))
            setWorkingPrinter(def);
    }

    private void setWorkingPrinter(String printer) {
        appRepository.saveWorkingPrinter(printer);
    }

    public String getDefaultPrinter() {
        return appRepository.getDefaultPrinter(activeMenu);
    }

    public void setDefaultPrinter(int menu, String name) {
        appRepository.saveDefaultPrinter(menu, name);
        setWorkingPrinter(name);
    }

    public boolean isXT2WorkingPrinter() {
        return getDefaultPrinter().equalsIgnoreCase(Constants.BXL_XT2);
    }

    public boolean isXT5WorkingPrinter() {
        return getDefaultPrinter().equalsIgnoreCase(Constants.BXL_XT5);
    }

    public boolean isL310WorkingPrinter() {
        return getDefaultPrinter().equalsIgnoreCase(Constants.BXL_SPP_L310);
    }

    public boolean isR310WorkingPrinter() {
        return getDefaultPrinter().equalsIgnoreCase(Constants.BXL_SPP_R310);
    }

    public String getPrinterAddress() {
        return appRepository.getPrinterAddress();
    }

    public BxlPrintOptions getLabelOptions() {
        BxlPrintOptions options = new BxlPrintOptions();
        if (isL310WorkingPrinter()) {
            options.name = Constants.BXL_SPP_L310;
            options.labelWidth = Constants.LABEL_NEW_SIZE;
        } else if (isXT2WorkingPrinter()) {
            options.name = Constants.BXL_XT2;
            options.labelWidth = Constants.LABEL_XT_SIZE;
        } else {
            options.name = Constants.BXL_XT5;
            options.labelWidth = Constants.LABEL_XT_SIZE;
        }
        options.density = getPrintDensity();
        return options;
    }

    public BxlPPP getPrintDensity() {
        if (isL310WorkingPrinter())
            return BxlPPP.DPI_203;
        return BxlPPP.DPI_300;
    }

    public void saveBluetoothAddress(BluetoothDevice device) {
        appRepository.saveBluetoothPrinterAddress(device.getAddress());
    }

    public void updatePrintItems(List items) {
        if (items.size() > 0)
            view.updateAfterPrint((PrintItem) items.get(0));
    }


    public void updatePrintItem(IItem item) {

    }

    @Override
    public void getDataToPrint(){

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
        if(finalPrinterAddress.isEmpty()){
            view.showChoosePrinter();
            return Observable.just(false);
        }
        return ObservableCall.observable(() ->
                {
                    return BXLPrinter.getInstance().connect(finalPrinterAddress);//Connect wifi port
                }
        ).map(connected -> connected);
    }


}
