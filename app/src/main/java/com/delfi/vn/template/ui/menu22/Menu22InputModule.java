package com.delfi.vn.template.ui.menu22;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class Menu22InputModule {
    @Binds
    abstract Menu22InputContract.View provideMenu21InputActivity(Menu22InputActivity activity);
}
