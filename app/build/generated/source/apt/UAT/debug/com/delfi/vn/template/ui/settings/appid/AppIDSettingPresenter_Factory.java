// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.ui.settings.appid;

import com.delfi.vn.template.repositories.AppRepository;
import dagger.internal.Factory;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AppIDSettingPresenter_Factory implements Factory<AppIDSettingPresenter> {
  private final Provider<AppIDSettingContract.View> viewProvider;

  private final Provider<AppRepository> appRepositoryProvider;

  public AppIDSettingPresenter_Factory(Provider<AppIDSettingContract.View> viewProvider,
      Provider<AppRepository> appRepositoryProvider) {
    this.viewProvider = viewProvider;
    this.appRepositoryProvider = appRepositoryProvider;
  }

  @Override
  public AppIDSettingPresenter get() {
    return new AppIDSettingPresenter(viewProvider.get(), appRepositoryProvider.get());
  }

  public static AppIDSettingPresenter_Factory create(
      Provider<AppIDSettingContract.View> viewProvider,
      Provider<AppRepository> appRepositoryProvider) {
    return new AppIDSettingPresenter_Factory(viewProvider, appRepositoryProvider);
  }

  public static AppIDSettingPresenter newInstance(AppIDSettingContract.View view,
      AppRepository appRepository) {
    return new AppIDSettingPresenter(view, appRepository);
  }
}
