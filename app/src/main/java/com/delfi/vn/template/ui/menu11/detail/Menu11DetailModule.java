package com.delfi.vn.template.ui.menu11.detail;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class Menu11DetailModule {
    @Binds
    abstract Menu11DetailContract.View provideMenu11Activity(Menu11DetailActivity activity);
}
