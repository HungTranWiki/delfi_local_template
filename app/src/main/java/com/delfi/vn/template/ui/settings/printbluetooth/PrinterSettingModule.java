package com.delfi.vn.template.ui.settings.printbluetooth;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PrinterSettingModule {

    @Binds
    abstract PrinterSettingContract.View providePrinterSettingContractView(PrinterSettingActivity activity);
}
