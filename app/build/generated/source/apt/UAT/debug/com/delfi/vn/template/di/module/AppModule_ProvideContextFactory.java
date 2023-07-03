// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.di.module;

import android.app.Application;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AppModule_ProvideContextFactory implements Factory<Context> {
  private final AppModule module;

  private final Provider<Application> applicationProvider;

  public AppModule_ProvideContextFactory(AppModule module,
      Provider<Application> applicationProvider) {
    this.module = module;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public Context get() {
    return provideContext(module, applicationProvider.get());
  }

  public static AppModule_ProvideContextFactory create(AppModule module,
      Provider<Application> applicationProvider) {
    return new AppModule_ProvideContextFactory(module, applicationProvider);
  }

  public static Context provideContext(AppModule instance, Application application) {
    return Preconditions.checkNotNull(instance.provideContext(application), "Cannot return null from a non-@Nullable @Provides method");
  }
}
