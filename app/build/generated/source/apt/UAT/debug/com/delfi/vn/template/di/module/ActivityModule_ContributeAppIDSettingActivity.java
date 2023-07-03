package com.delfi.vn.template.di.module;

import com.delfi.vn.template.ui.settings.appid.AppIDSettingActivity;
import com.delfi.vn.template.ui.settings.appid.AppIDSettingModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      ActivityModule_ContributeAppIDSettingActivity.AppIDSettingActivitySubcomponent.class
)
public abstract class ActivityModule_ContributeAppIDSettingActivity {
  private ActivityModule_ContributeAppIDSettingActivity() {}

  @Binds
  @IntoMap
  @ClassKey(AppIDSettingActivity.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      AppIDSettingActivitySubcomponent.Factory builder);

  @Subcomponent(modules = AppIDSettingModule.class)
  public interface AppIDSettingActivitySubcomponent extends AndroidInjector<AppIDSettingActivity> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<AppIDSettingActivity> {}
  }
}
