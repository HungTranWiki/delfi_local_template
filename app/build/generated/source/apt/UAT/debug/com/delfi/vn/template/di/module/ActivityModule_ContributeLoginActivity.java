package com.delfi.vn.template.di.module;

import com.delfi.vn.template.ui.login.LoginActivity;
import com.delfi.vn.template.ui.login.LoginViewModule;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = ActivityModule_ContributeLoginActivity.LoginActivitySubcomponent.class)
public abstract class ActivityModule_ContributeLoginActivity {
  private ActivityModule_ContributeLoginActivity() {}

  @Binds
  @IntoMap
  @ClassKey(LoginActivity.class)
  abstract AndroidInjector.Factory<?> bindAndroidInjectorFactory(
      LoginActivitySubcomponent.Factory builder);

  @Subcomponent(modules = LoginViewModule.class)
  public interface LoginActivitySubcomponent extends AndroidInjector<LoginActivity> {
    @Subcomponent.Factory
    interface Factory extends AndroidInjector.Factory<LoginActivity> {}
  }
}
