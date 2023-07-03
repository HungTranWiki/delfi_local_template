package com.delfi.vn.template.di.module;

import com.delfi.vn.template.ui.menu11.detail.Menu11DetailActivity;
import com.delfi.vn.template.ui.menu11.detail.Menu11DetailModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      ActivityModule_ContributeMenu11DetailActivity.Menu11DetailActivitySubcomponent.class
)
public abstract class ActivityModule_ContributeMenu11DetailActivity {
  private ActivityModule_ContributeMenu11DetailActivity() {}

  @Binds
  @IntoMap
  @ClassKey(Menu11DetailActivity.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      Menu11DetailActivitySubcomponent.Factory builder);

  @Subcomponent(modules = Menu11DetailModule.class)
  public interface Menu11DetailActivitySubcomponent extends AndroidInjector<Menu11DetailActivity> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<Menu11DetailActivity> {}
  }
}
