// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.repositories;

import dagger.internal.Factory;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class WarehouseLocalRepoImpl_Factory implements Factory<WarehouseLocalRepoImpl> {
  private final Provider<LocalRepository> localRepoProvider;

  public WarehouseLocalRepoImpl_Factory(Provider<LocalRepository> localRepoProvider) {
    this.localRepoProvider = localRepoProvider;
  }

  @Override
  public WarehouseLocalRepoImpl get() {
    return new WarehouseLocalRepoImpl(localRepoProvider.get());
  }

  public static WarehouseLocalRepoImpl_Factory create(Provider<LocalRepository> localRepoProvider) {
    return new WarehouseLocalRepoImpl_Factory(localRepoProvider);
  }

  public static WarehouseLocalRepoImpl newInstance(LocalRepository localRepo) {
    return new WarehouseLocalRepoImpl(localRepo);
  }
}
