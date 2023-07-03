package com.delfi.vn.template.ui.settings.appid;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppIDSettingModule {

    @Binds
    abstract AppIDSettingContract.View provideAppIDSettingActivity(AppIDSettingActivity activity);
}
