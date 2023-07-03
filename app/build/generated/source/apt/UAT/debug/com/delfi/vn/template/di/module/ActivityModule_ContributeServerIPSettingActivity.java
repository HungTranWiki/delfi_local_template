package com.delfi.vn.template.di.module;

import com.delfi.vn.template.ui.settings.serverip.ServerIPSettingActivity;
import com.delfi.vn.template.ui.settings.serverip.ServerIPSettingModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      ActivityModule_ContributeServerIPSettingActivity.ServerIPSettingActivitySubcomponent.class
)
public abstract class ActivityModule_ContributeServerIPSettingActivity {
  private ActivityModule_ContributeServerIPSettingActivity() {}

  @Binds
  @IntoMap
  @ClassKey(ServerIPSettingActivity.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      ServerIPSettingActivitySubcomponent.Factory builder);

  @Subcomponent(modules = ServerIPSettingModule.class)
  public interface ServerIPSettingActivitySubcomponent
      extends AndroidInjector<ServerIPSettingActivity> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<ServerIPSettingActivity> {}
  }
}
