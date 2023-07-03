// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.repositories;

import dagger.internal.Factory;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class PhoneLocalRepoImpl_Factory implements Factory<PhoneLocalRepoImpl> {
  private final Provider<LocalRepository> localRepoProvider;

  public PhoneLocalRepoImpl_Factory(Provider<LocalRepository> localRepoProvider) {
    this.localRepoProvider = localRepoProvider;
  }

  @Override
  public PhoneLocalRepoImpl get() {
    return new PhoneLocalRepoImpl(localRepoProvider.get());
  }

  public static PhoneLocalRepoImpl_Factory create(Provider<LocalRepository> localRepoProvider) {
    return new PhoneLocalRepoImpl_Factory(localRepoProvider);
  }

  public static PhoneLocalRepoImpl newInstance(LocalRepository localRepo) {
    return new PhoneLocalRepoImpl(localRepo);
  }
}
