// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.ui.settings.printbluetooth;

import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;
import dagger.internal.Factory;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class PrinterSettingPresenter_Factory implements Factory<PrinterSettingPresenter> {
  private final Provider<PrinterSettingContract.View> viewProvider;

  private final Provider<SchedulerListener> schedulerListenerProvider;

  private final Provider<AppRepository> appRepositoryProvider;

  public PrinterSettingPresenter_Factory(Provider<PrinterSettingContract.View> viewProvider,
      Provider<SchedulerListener> schedulerListenerProvider,
      Provider<AppRepository> appRepositoryProvider) {
    this.viewProvider = viewProvider;
    this.schedulerListenerProvider = schedulerListenerProvider;
    this.appRepositoryProvider = appRepositoryProvider;
  }

  @Override
  public PrinterSettingPresenter get() {
    return new PrinterSettingPresenter(viewProvider.get(), schedulerListenerProvider.get(), appRepositoryProvider.get());
  }

  public static PrinterSettingPresenter_Factory create(
      Provider<PrinterSettingContract.View> viewProvider,
      Provider<SchedulerListener> schedulerListenerProvider,
      Provider<AppRepository> appRepositoryProvider) {
    return new PrinterSettingPresenter_Factory(viewProvider, schedulerListenerProvider, appRepositoryProvider);
  }

  public static PrinterSettingPresenter newInstance(PrinterSettingContract.View view,
      SchedulerListener schedulerListener, AppRepository appRepository) {
    return new PrinterSettingPresenter(view, schedulerListener, appRepository);
  }
}
