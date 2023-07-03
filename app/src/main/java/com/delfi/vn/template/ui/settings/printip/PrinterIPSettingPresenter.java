package com.delfi.vn.template.ui.settings.printip;

import com.delfi.vn.template.ui.base.BasePresenter;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.utils.CommonUtil;

import javax.inject.Inject;

public class PrinterIPSettingPresenter extends BasePresenter<PrinterIPSettingContract.View>
        implements PrinterIPSettingContract.Presenter {

    private AppRepository appRepository;

    @Inject
    public PrinterIPSettingPresenter(PrinterIPSettingContract.View view,
                                     AppRepository appRepository) {
        super(view);
        this.appRepository = appRepository;
    }

    @Override
    public void save(String ip, String port, String dpi) {
        if (ip.length() == 0) {
            view.validateError();
            return;
        }
        if (CommonUtil.isIPV4(ip)) {
            appRepository.savePrinterIP(ip, Integer.valueOf(port), dpi);
            view.saveChanged();
        } else {
            view.validateError();
        }
    }
}
