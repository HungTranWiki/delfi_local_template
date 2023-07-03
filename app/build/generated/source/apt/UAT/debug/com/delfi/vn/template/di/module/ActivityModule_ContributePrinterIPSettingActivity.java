package com.delfi.vn.template.di.module;

import com.delfi.vn.template.ui.settings.printip.PrinterIPSettingActivity;
import com.delfi.vn.template.ui.settings.printip.PrinterIPSettingModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      ActivityModule_ContributePrinterIPSettingActivity.PrinterIPSettingActivitySubcomponent.class
)
public abstract class ActivityModule_ContributePrinterIPSettingActivity {
  private ActivityModule_ContributePrinterIPSettingActivity() {}

  @Binds
  @IntoMap
  @ClassKey(PrinterIPSettingActivity.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      PrinterIPSettingActivitySubcomponent.Factory builder);

  @Subcomponent(modules = PrinterIPSettingModule.class)
  public interface PrinterIPSettingActivitySubcomponent
      extends AndroidInjector<PrinterIPSettingActivity> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<PrinterIPSettingActivity> {}
  }
}
