package com.delfi.vn.template.di.component;

import android.app.Application;

import com.delfi.vn.template.MyApplication;
import com.delfi.vn.template.di.module.ActivityModule;
import com.delfi.vn.template.di.module.ApiModule;
import com.delfi.vn.template.di.module.AppModule;
import com.delfi.vn.template.di.module.DatabaseModule;
import com.delfi.vn.template.di.module.RepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityModule.class, ApiModule.class,
        DatabaseModule.class, RepositoryModule.class
})
public interface AppComponent {
    void inject(MyApplication application);

    void inject(AppModule appModule);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder appModule(AppModule module);

        @BindsInstance
        Builder apiModule(ApiModule module);

        @BindsInstance
        Builder databaseModule(DatabaseModule module);
//
//        @BindsInstance
//        Builder repositoryModule(RepositoryModule module);

        AppComponent build();
    }
}
