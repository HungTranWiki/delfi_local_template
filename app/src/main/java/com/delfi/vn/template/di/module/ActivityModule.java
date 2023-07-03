package com.delfi.vn.template.di.module;

import com.delfi.vn.template.ui.login.LoginActivity;
import com.delfi.vn.template.ui.login.LoginViewModule;
import com.delfi.vn.template.ui.main.MainActivity;
import com.delfi.vn.template.ui.main.MainViewModule;
import com.delfi.vn.template.ui.menu11.detail.Menu11DetailActivity;
import com.delfi.vn.template.ui.menu11.detail.Menu11DetailModule;
import com.delfi.vn.template.ui.menu11.input.Menu11InputActivity;
import com.delfi.vn.template.ui.menu11.input.Menu11InputModule;
import com.delfi.vn.template.ui.menu21.Menu21InputActivity;
import com.delfi.vn.template.ui.menu21.Menu21InputModule;
import com.delfi.vn.template.ui.menu22.Menu22InputActivity;
import com.delfi.vn.template.ui.menu22.Menu22InputModule;
import com.delfi.vn.template.ui.settings.appid.AppIDSettingActivity;
import com.delfi.vn.template.ui.settings.appid.AppIDSettingModule;
import com.delfi.vn.template.ui.settings.printip.PrinterIPSettingActivity;
import com.delfi.vn.template.ui.settings.printip.PrinterIPSettingModule;
import com.delfi.vn.template.ui.settings.serverip.ServerIPSettingActivity;
import com.delfi.vn.template.ui.settings.serverip.ServerIPSettingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = {LoginViewModule.class})
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(modules = {MainViewModule.class})
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = {ServerIPSettingModule.class})
    abstract ServerIPSettingActivity contributeServerIPSettingActivity();

    @ContributesAndroidInjector(modules = {AppIDSettingModule.class})
    abstract AppIDSettingActivity contributeAppIDSettingActivity();

    @ContributesAndroidInjector(modules = {PrinterIPSettingModule.class})
    abstract PrinterIPSettingActivity contributePrinterIPSettingActivity();

    @ContributesAndroidInjector(modules = {Menu11InputModule.class})
    abstract Menu11InputActivity contributeMenu11InputActivity();

    @ContributesAndroidInjector(modules = {Menu11DetailModule.class})
    abstract Menu11DetailActivity contributeMenu11DetailActivity();

    @ContributesAndroidInjector(modules = {Menu21InputModule.class})
    abstract Menu21InputActivity contributeMenu21InputActivity();

    @ContributesAndroidInjector(modules = {Menu22InputModule.class})
    abstract Menu22InputActivity contributeMenu22InputActivity();

}
