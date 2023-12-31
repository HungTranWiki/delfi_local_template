// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.di.module;

import com.delfi.vn.template.utils.rxscheduler.SchedulerListener;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AppModule_ProvideSchedulerListenerFactory implements Factory<SchedulerListener> {
  private final AppModule module;

  public AppModule_ProvideSchedulerListenerFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public SchedulerListener get() {
    return provideSchedulerListener(module);
  }

  public static AppModule_ProvideSchedulerListenerFactory create(AppModule module) {
    return new AppModule_ProvideSchedulerListenerFactory(module);
  }

  public static SchedulerListener provideSchedulerListener(AppModule instance) {
    return Preconditions.checkNotNull(instance.provideSchedulerListener(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
