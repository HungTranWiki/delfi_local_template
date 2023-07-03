// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.ui.menu11.detail;

import com.delfi.vn.template.ui.base.BaseActivity_MembersInjector;
import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;
import dagger.MembersInjector;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class Menu11DetailActivity_MembersInjector implements MembersInjector<Menu11DetailActivity> {
  private final Provider<SchedulerListener> schedulerListenerProvider;

  private final Provider<Menu11DetailPresenter> presenterProvider;

  public Menu11DetailActivity_MembersInjector(Provider<SchedulerListener> schedulerListenerProvider,
      Provider<Menu11DetailPresenter> presenterProvider) {
    this.schedulerListenerProvider = schedulerListenerProvider;
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<Menu11DetailActivity> create(
      Provider<SchedulerListener> schedulerListenerProvider,
      Provider<Menu11DetailPresenter> presenterProvider) {
    return new Menu11DetailActivity_MembersInjector(schedulerListenerProvider, presenterProvider);}

  @Override
  public void injectMembers(Menu11DetailActivity instance) {
    BaseActivity_MembersInjector.injectSchedulerListener(instance, schedulerListenerProvider.get());
    injectPresenter(instance, presenterProvider.get());
  }

  public static void injectPresenter(Menu11DetailActivity instance,
      Menu11DetailPresenter presenter) {
    instance.presenter = presenter;
  }
}
