package com.delfi.vn.template.di.module;

import com.delfi.vn.template.ui.menu22.Menu22InputActivity;
import com.delfi.vn.template.ui.menu22.Menu22InputModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents = ActivityModule_ContributeMenu22InputActivity.Menu22InputActivitySubcomponent.class
)
public abstract class ActivityModule_ContributeMenu22InputActivity {
  private ActivityModule_ContributeMenu22InputActivity() {}

  @Binds
  @IntoMap
  @ClassKey(Menu22InputActivity.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      Menu22InputActivitySubcomponent.Factory builder);

  @Subcomponent(modules = Menu22InputModule.class)
  public interface Menu22InputActivitySubcomponent extends AndroidInjector<Menu22InputActivity> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<Menu22InputActivity> {}
  }
}
