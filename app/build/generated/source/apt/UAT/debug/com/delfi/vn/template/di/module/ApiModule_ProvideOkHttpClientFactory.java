// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.di.module;

import com.delfi.vn.template.services.api.AuthenticationInterceptor;
import com.delfi.vn.template.services.api.NetworkInterceptor;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ApiModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final ApiModule module;

  private final Provider<Cache> cacheProvider;

  private final Provider<AuthenticationInterceptor> authInterceptorProvider;

  private final Provider<NetworkInterceptor> networkInterceptorProvider;

  public ApiModule_ProvideOkHttpClientFactory(ApiModule module, Provider<Cache> cacheProvider,
      Provider<AuthenticationInterceptor> authInterceptorProvider,
      Provider<NetworkInterceptor> networkInterceptorProvider) {
    this.module = module;
    this.cacheProvider = cacheProvider;
    this.authInterceptorProvider = authInterceptorProvider;
    this.networkInterceptorProvider = networkInterceptorProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(module, cacheProvider.get(), authInterceptorProvider.get(), networkInterceptorProvider.get());
  }

  public static ApiModule_ProvideOkHttpClientFactory create(ApiModule module,
      Provider<Cache> cacheProvider, Provider<AuthenticationInterceptor> authInterceptorProvider,
      Provider<NetworkInterceptor> networkInterceptorProvider) {
    return new ApiModule_ProvideOkHttpClientFactory(module, cacheProvider, authInterceptorProvider, networkInterceptorProvider);
  }

  public static OkHttpClient provideOkHttpClient(ApiModule instance, Cache cache,
      AuthenticationInterceptor authInterceptor, NetworkInterceptor networkInterceptor) {
    return Preconditions.checkNotNull(instance.provideOkHttpClient(cache, authInterceptor, networkInterceptor), "Cannot return null from a non-@Nullable @Provides method");
  }
}