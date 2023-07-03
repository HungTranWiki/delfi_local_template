package com.delfi.vn.template.ui.base.recordsandprinter;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;

import com.delfi.core.controls.editableview.interfaces.IItem;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.ui.base.BasePresenter;
import com.delfi.vn.template.utils.Constants;
import com.delfi.vn.template.utils.printer.constant.BxlPPP;
import com.delfi.vn.template.utils.printer.model.BxlPrintOptions;

import java.util.List;

public abstract class BaseRecordsAndPrintPresenter<V extends BaseRecordsAndPrintContract.View> extends BasePresenter<V> {
    protected final AppRepository appRepository;
    private int activeMenu;

    protected BaseRecordsAndPrintPresenter(V view, AppRepository appRepository) {
        super(view);
        this.appRepository = appRepository;
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
            view.updateAfterPrint((IItem) items.get(0));
    }


    public void updatePrintItem(IItem item) {

    }

    public void saveLastNMVN(String NMVN) {
        appRepository.saveNMVN(NMVN);
    }

    public String getLastNMVN() {
        return appRepository.getNMVN();
    }
}
