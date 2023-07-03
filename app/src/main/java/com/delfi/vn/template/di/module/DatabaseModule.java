package com.delfi.vn.template.di.module;

import android.content.Context;

import com.delfi.core.sqlite.DbHelper;
import com.delfi.vn.template.di.customanotation.DatabaseInfo;
import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Email;
import com.delfi.vn.template.models.dbmodels.Phone;
import com.delfi.vn.template.models.dbmodels.Product;
import com.delfi.vn.template.models.dbmodels.Receipt11Detail;
import com.delfi.vn.template.models.dbmodels.SavedMenu11;
import com.delfi.vn.template.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private List<Class> tblEntries = new ArrayList<Class>() {
        {
            add(Product.class);
            add(Receipt11Detail.class);
            add(SavedMenu11.class);
            add(Warehouse.class);
            add(Email.class);
            add(Phone.class);
        }
    };

    @Singleton
    @Provides
    @SuppressWarnings("rawtypes")
    DbHelper provideDbHelper(@NotNull Context context, @DatabaseInfo String databaseName, @DatabaseInfo Integer version) {
        new DbHelper<>(context, databaseName, version, tblEntries);
        return DbHelper.getInstance(context);
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return Constants.DB_NAME;
    }

    @Provides
    @DatabaseInfo
    Integer provideDatabaseVersion() {
        return Constants.DB_VERSION;
    }
}
