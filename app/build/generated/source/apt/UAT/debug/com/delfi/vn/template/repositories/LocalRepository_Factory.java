// Generated by Dagger (https://dagger.dev).
package com.delfi.vn.template.repositories;

import com.delfi.core.sqlite.DbHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class LocalRepository_Factory implements Factory<LocalRepository> {
  private final Provider<DbHelper> dbHelperProvider;

  public LocalRepository_Factory(Provider<DbHelper> dbHelperProvider) {
    this.dbHelperProvider = dbHelperProvider;
  }

  @Override
  public LocalRepository get() {
    return new LocalRepository(dbHelperProvider.get());
  }

  public static LocalRepository_Factory create(Provider<DbHelper> dbHelperProvider) {
    return new LocalRepository_Factory(dbHelperProvider);
  }

  public static LocalRepository newInstance(DbHelper dbHelper) {
    return new LocalRepository(dbHelper);
  }
}
