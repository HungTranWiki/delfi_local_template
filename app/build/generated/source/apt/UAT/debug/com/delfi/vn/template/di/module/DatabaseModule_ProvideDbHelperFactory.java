// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.di.module;

import android.content.Context;
import com.delfi.core.sqlite.DbHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DatabaseModule_ProvideDbHelperFactory implements Factory<DbHelper> {
  private final DatabaseModule module;

  private final Provider<Context> contextProvider;

  private final Provider<String> databaseNameProvider;

  private final Provider<Integer> versionProvider;

  public DatabaseModule_ProvideDbHelperFactory(DatabaseModule module,
      Provider<Context> contextProvider, Provider<String> databaseNameProvider,
      Provider<Integer> versionProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
    this.databaseNameProvider = databaseNameProvider;
    this.versionProvider = versionProvider;
  }

  @Override
  public DbHelper get() {
    return provideDbHelper(module, contextProvider.get(), databaseNameProvider.get(), versionProvider.get());
  }

  public static DatabaseModule_ProvideDbHelperFactory create(DatabaseModule module,
      Provider<Context> contextProvider, Provider<String> databaseNameProvider,
      Provider<Integer> versionProvider) {
    return new DatabaseModule_ProvideDbHelperFactory(module, contextProvider, databaseNameProvider, versionProvider);
  }

  public static DbHelper provideDbHelper(DatabaseModule instance, Context context,
      String databaseName, Integer version) {
    return Preconditions.checkNotNull(instance.provideDbHelper(context, databaseName, version), "Cannot return null from a non-@Nullable @Provides method");
  }
}