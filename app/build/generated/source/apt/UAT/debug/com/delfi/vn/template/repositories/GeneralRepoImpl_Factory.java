// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.repositories;

import com.delfi.vn.template.services.api.ApiRepositoryImpl;
import dagger.internal.Factory;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class GeneralRepoImpl_Factory implements Factory<GeneralRepoImpl> {
  private final Provider<ApiRepositoryImpl> apiRepositoryImplProvider;

  private final Provider<AppRepository> appRepositoryProvider;

  private final Provider<WarehouseLocalRepo> warehouseLocalRepoProvider;

  private final Provider<ProductLocalRepo> productLocalRepoProvider;

  public GeneralRepoImpl_Factory(Provider<ApiRepositoryImpl> apiRepositoryImplProvider,
      Provider<AppRepository> appRepositoryProvider,
      Provider<WarehouseLocalRepo> warehouseLocalRepoProvider,
      Provider<ProductLocalRepo> productLocalRepoProvider) {
    this.apiRepositoryImplProvider = apiRepositoryImplProvider;
    this.appRepositoryProvider = appRepositoryProvider;
    this.warehouseLocalRepoProvider = warehouseLocalRepoProvider;
    this.productLocalRepoProvider = productLocalRepoProvider;
  }

  @Override
  public GeneralRepoImpl get() {
    return new GeneralRepoImpl(apiRepositoryImplProvider.get(), appRepositoryProvider.get(), warehouseLocalRepoProvider.get(), productLocalRepoProvider.get());
  }

  public static GeneralRepoImpl_Factory create(
      Provider<ApiRepositoryImpl> apiRepositoryImplProvider,
      Provider<AppRepository> appRepositoryProvider,
      Provider<WarehouseLocalRepo> warehouseLocalRepoProvider,
      Provider<ProductLocalRepo> productLocalRepoProvider) {
    return new GeneralRepoImpl_Factory(apiRepositoryImplProvider, appRepositoryProvider, warehouseLocalRepoProvider, productLocalRepoProvider);
  }

  public static GeneralRepoImpl newInstance(ApiRepositoryImpl apiRepositoryImpl,
      AppRepository appRepository, WarehouseLocalRepo warehouseLocalRepo,
      ProductLocalRepo productLocalRepo) {
    return new GeneralRepoImpl(apiRepositoryImpl, appRepository, warehouseLocalRepo, productLocalRepo);
  }
}
