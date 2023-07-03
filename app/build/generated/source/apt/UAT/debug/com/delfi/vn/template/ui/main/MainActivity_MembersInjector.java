// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.ui.main;

import com.delfi.vn.template.ui.base.BaseActivity_MembersInjector;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;
import dagger.MembersInjector;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<SchedulerListener> schedulerListenerProvider;

  private final Provider<MainPresenter> presenterProvider;

  public MainActivity_MembersInjector(Provider<SchedulerListener> schedulerListenerProvider,
      Provider<MainPresenter> presenterProvider) {
    this.schedulerListenerProvider = schedulerListenerProvider;
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<SchedulerListener> schedulerListenerProvider,
      Provider<MainPresenter> presenterProvider) {
    return new MainActivity_MembersInjector(schedulerListenerProvider, presenterProvider);}

  @Override
  public void injectMembers(MainActivity instance) {
    BaseActivity_MembersInjector.injectSchedulerListener(instance, schedulerListenerProvider.get());
    injectPresenter(instance, presenterProvider.get());
  }

  public static void injectPresenter(MainActivity instance, MainPresenter presenter) {
    instance.presenter = presenter;
  }
}