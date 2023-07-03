package com.delfi.vn.template.ui.menu11.input;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class Menu11InputModule {
    @Binds
    abstract Menu11InputContract.View provideMenu11InputActivity(Menu11InputActivity activity);
}
