// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.ui.settings.printip;

import com.delfi.vn.template.repositories.AppRepository;
import dagger.internal.Factory;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class PrinterIPSettingPresenter_Factory implements Factory<PrinterIPSettingPresenter> {
  private final Provider<PrinterIPSettingContract.View> viewProvider;

  private final Provider<AppRepository> appRepositoryProvider;

  public PrinterIPSettingPresenter_Factory(Provider<PrinterIPSettingContract.View> viewProvider,
      Provider<AppRepository> appRepositoryProvider) {
    this.viewProvider = viewProvider;
    this.appRepositoryProvider = appRepositoryProvider;
  }

  @Override
  public PrinterIPSettingPresenter get() {
    return new PrinterIPSettingPresenter(viewProvider.get(), appRepositoryProvider.get());
  }

  public static PrinterIPSettingPresenter_Factory create(
      Provider<PrinterIPSettingContract.View> viewProvider,
      Provider<AppRepository> appRepositoryProvider) {
    return new PrinterIPSettingPresenter_Factory(viewProvider, appRepositoryProvider);
  }

  public static PrinterIPSettingPresenter newInstance(PrinterIPSettingContract.View view,
      AppRepository appRepository) {
    return new PrinterIPSettingPresenter(view, appRepository);
  }
}