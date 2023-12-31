// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.ui.settings.serverip;

import com.delfi.vn.template.ui.base.BaseActivity_MembersInjector;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;
import dagger.MembersInjector;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ServerIPSettingActivity_MembersInjector implements MembersInjector<ServerIPSettingActivity> {
  private final Provider<SchedulerListener> schedulerListenerProvider;

  private final Provider<ServerIPSettingPresenter> presenterProvider;

  public ServerIPSettingActivity_MembersInjector(
      Provider<SchedulerListener> schedulerListenerProvider,
      Provider<ServerIPSettingPresenter> presenterProvider) {
    this.schedulerListenerProvider = schedulerListenerProvider;
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<ServerIPSettingActivity> create(
      Provider<SchedulerListener> schedulerListenerProvider,
      Provider<ServerIPSettingPresenter> presenterProvider) {
    return new ServerIPSettingActivity_MembersInjector(schedulerListenerProvider, presenterProvider);}

  @Override
  public void injectMembers(ServerIPSettingActivity instance) {
    BaseActivity_MembersInjector.injectSchedulerListener(instance, schedulerListenerProvider.get());
    injectPresenter(instance, presenterProvider.get());
  }

  public static void injectPresenter(ServerIPSettingActivity instance,
      ServerIPSettingPresenter presenter) {
    instance.presenter = presenter;
  }
}
