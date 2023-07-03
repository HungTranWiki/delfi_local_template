// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.repositories;

import dagger.internal.Factory;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class Receipt11LocalRepoImpl_Factory implements Factory<Receipt11LocalRepoImpl> {
  private final Provider<LocalRepository> localRepoProvider;

  public Receipt11LocalRepoImpl_Factory(Provider<LocalRepository> localRepoProvider) {
    this.localRepoProvider = localRepoProvider;
  }

  @Override
  public Receipt11LocalRepoImpl get() {
    return new Receipt11LocalRepoImpl(localRepoProvider.get());
  }

  public static Receipt11LocalRepoImpl_Factory create(Provider<LocalRepository> localRepoProvider) {
    return new Receipt11LocalRepoImpl_Factory(localRepoProvider);
  }

  public static Receipt11LocalRepoImpl newInstance(LocalRepository localRepo) {
    return new Receipt11LocalRepoImpl(localRepo);
  }
}