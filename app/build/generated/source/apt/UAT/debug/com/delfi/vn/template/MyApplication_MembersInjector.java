// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template;

import android.app.Activity;
import android.support.v4.app.Fragment;
import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MyApplication_MembersInjector implements MembersInjector<MyApplication> {
  private final Provider<DispatchingAndroidInjector<Activity>> activityDispatchingAndroidInjectorProvider;

  private final Provider<DispatchingAndroidInjector<Fragment>> fragmentDispatchingAndroidInjectorProvider;

  public MyApplication_MembersInjector(
      Provider<DispatchingAndroidInjector<Activity>> activityDispatchingAndroidInjectorProvider,
      Provider<DispatchingAndroidInjector<Fragment>> fragmentDispatchingAndroidInjectorProvider) {
    this.activityDispatchingAndroidInjectorProvider = activityDispatchingAndroidInjectorProvider;
    this.fragmentDispatchingAndroidInjectorProvider = fragmentDispatchingAndroidInjectorProvider;
  }

  public static MembersInjector<MyApplication> create(
      Provider<DispatchingAndroidInjector<Activity>> activityDispatchingAndroidInjectorProvider,
      Provider<DispatchingAndroidInjector<Fragment>> fragmentDispatchingAndroidInjectorProvider) {
    return new MyApplication_MembersInjector(activityDispatchingAndroidInjectorProvider, fragmentDispatchingAndroidInjectorProvider);}

  @Override
  public void injectMembers(MyApplication instance) {
    injectActivityDispatchingAndroidInjector(instance, activityDispatchingAndroidInjectorProvider.get());
    injectFragmentDispatchingAndroidInjector(instance, fragmentDispatchingAndroidInjectorProvider.get());
  }

  public static void injectActivityDispatchingAndroidInjector(MyApplication instance,
      DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector) {
    instance.activityDispatchingAndroidInjector = activityDispatchingAndroidInjector;
  }

  public static void injectFragmentDispatchingAndroidInjector(MyApplication instance,
      DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector) {
    instance.fragmentDispatchingAndroidInjector = fragmentDispatchingAndroidInjector;
  }
}