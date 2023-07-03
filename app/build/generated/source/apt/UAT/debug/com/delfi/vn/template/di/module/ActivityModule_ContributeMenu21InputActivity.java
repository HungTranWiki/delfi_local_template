package com.delfi.vn.template.di.module;

import com.delfi.vn.template.ui.menu21.Menu21InputActivity;
import com.delfi.vn.template.ui.menu21.Menu21InputModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents = ActivityModule_ContributeMenu21InputActivity.Menu21InputActivitySubcomponent.class
)
public abstract class ActivityModule_ContributeMenu21InputActivity {
  private ActivityModule_ContributeMenu21InputActivity() {}

  @Binds
  @IntoMap
  @ClassKey(Menu21InputActivity.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      Menu21InputActivitySubcomponent.Factory builder);

  @Subcomponent(modules = Menu21InputModule.class)
  public interface Menu21InputActivitySubcomponent extends AndroidInjector<Menu21InputActivity> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<Menu21InputActivity> {}
  }
}
