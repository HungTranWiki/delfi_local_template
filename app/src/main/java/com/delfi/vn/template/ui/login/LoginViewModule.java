package com.delfi.vn.template.ui.login;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class LoginViewModule {
    @Binds
    abstract LoginContract.View provideLoginView(LoginActivity loginActivity);
}
