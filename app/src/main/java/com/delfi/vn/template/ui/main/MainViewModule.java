package com.delfi.vn.template.ui.main;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainViewModule {
    @Binds
    abstract MainContract.View provideMainView(MainActivity mainActivity);

}
