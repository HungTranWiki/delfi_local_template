package com.delfi.vn.template.di.module;

import com.delfi.vn.template.ui.menu11.input.Menu11InputActivity;
import com.delfi.vn.template.ui.menu11.input.Menu11InputModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents = ActivityModule_ContributeMenu11InputActivity.Menu11InputActivitySubcomponent.class
)
public abstract class ActivityModule_ContributeMenu11InputActivity {
  private ActivityModule_ContributeMenu11InputActivity() {}

  @Binds
  @IntoMap
  @ClassKey(Menu11InputActivity.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      Menu11InputActivitySubcomponent.Factory builder);

  @Subcomponent(modules = Menu11InputModule.class)
  public interface Menu11InputActivitySubcomponent extends AndroidInjector<Menu11InputActivity> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<Menu11InputActivity> {}
  }
}
