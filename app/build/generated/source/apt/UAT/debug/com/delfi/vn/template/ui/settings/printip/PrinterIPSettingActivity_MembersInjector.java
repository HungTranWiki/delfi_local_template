// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.ui.settings.printip;

import com.delfi.vn.template.ui.base.BaseActivity_MembersInjector;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;
import dagger.MembersInjector;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class PrinterIPSettingActivity_MembersInjector implements MembersInjector<PrinterIPSettingActivity> {
  private final Provider<SchedulerListener> schedulerListenerProvider;

  private final Provider<PrinterIPSettingPresenter> presenterProvider;

  public PrinterIPSettingActivity_MembersInjector(
      Provider<SchedulerListener> schedulerListenerProvider,
      Provider<PrinterIPSettingPresenter> presenterProvider) {
    this.schedulerListenerProvider = schedulerListenerProvider;
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<PrinterIPSettingActivity> create(
      Provider<SchedulerListener> schedulerListenerProvider,
      Provider<PrinterIPSettingPresenter> presenterProvider) {
    return new PrinterIPSettingActivity_MembersInjector(schedulerListenerProvider, presenterProvider);}

  @Override
  public void injectMembers(PrinterIPSettingActivity instance) {
    BaseActivity_MembersInjector.injectSchedulerListener(instance, schedulerListenerProvider.get());
    injectPresenter(instance, presenterProvider.get());
  }

  public static void injectPresenter(PrinterIPSettingActivity instance,
      PrinterIPSettingPresenter presenter) {
    instance.presenter = presenter;
  }
}
