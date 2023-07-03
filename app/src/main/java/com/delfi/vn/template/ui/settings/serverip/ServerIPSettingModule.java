package com.delfi.vn.template.ui.settings.serverip;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ServerIPSettingModule {
    @Binds
    abstract ServerIPSettingContract.View provideServerIPSettingActivity(ServerIPSettingActivity activity);
}
