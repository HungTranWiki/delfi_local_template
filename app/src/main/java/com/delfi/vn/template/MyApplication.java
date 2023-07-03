package com.delfi.vn.template;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.delfi.core.CoreApplication;
import com.delfi.core.common.SharedManager;
import com.delfi.core.log.ExceptionHandler;
import com.delfi.core.log.LogFileSize;
import com.delfi.core.log.LogLevel;
import com.delfi.core.log.LogSetting;
import com.delfi.core.log.Logger;
import com.delfi.core.scanner.DelfiScannerHandler;
import com.delfi.vn.template.di.component.DaggerAppComponent;
import com.delfi.vn.template.di.module.ApiModule;
import com.delfi.vn.template.di.module.AppModule;
import com.delfi.vn.template.di.module.DatabaseModule;
import com.delfi.vn.template.utils.Constants;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MyApplication extends Application implements HasActivityInjector, HasSupportFragmentInjector {
    private static WeakReference<Context> contextWeakReference;
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public static Context getContext() {
        return contextWeakReference.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        contextWeakReference = new WeakReference<>(getApplicationContext());
        DaggerAppComponent.builder().application(this)
                .appModule(new AppModule())
                .apiModule(new ApiModule())
                .databaseModule(new DatabaseModule())
                .build().inject(this);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        /**
         * Init logger
         */
        CoreApplication.setContext(getApplicationContext());
        LogSetting logSetting = new LogSetting(LogLevel.ERROR, LogFileSize.MB_5);
        Logger.getInstance().setLogSetting(logSetting);
        /**
         * Create scanner instance and default decode to Keyboard
         */
        DelfiScannerHandler.getInstance(getApplicationContext()).enableScanner();
        DelfiScannerHandler.getInstance(getApplicationContext()).setDecodeResultType(1);//0: usemsg; 1: keyboardmsg; 2: copypaste

        String url = SharedManager.getInstance(this).getString(Constants.URL);
        if (url.isEmpty())
            SharedManager.getInstance(this).putString(Constants.URL, "");
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

}
