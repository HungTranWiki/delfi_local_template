package com.delfi.vn.template.ui.menu21;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class Menu21InputModule {
    @Binds
    abstract Menu21InputContract.View provideMenu21InputActivity(Menu21InputActivity activity);
}
