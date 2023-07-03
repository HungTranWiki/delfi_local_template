package com.delfi.vn.template.ui.settings.printip;

import dagger.Binds;
import dagger.Module;
import static com.delfi.vn.template.ui.settings.printip.PrinterIPSettingContract.*;
@Module
public abstract class PrinterIPSettingModule {
    @Binds
    abstract View providePrinterIPSettingActivity(PrinterIPSettingActivity activity);
}
