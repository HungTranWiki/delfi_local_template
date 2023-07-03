package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.servermodels.User;
import com.delfi.vn.template.services.api.ApiRepositoryImpl;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.functions.Function;

public class UserRepositoryImpl implements UserRepository {
    private AppRepository appRepository;

    @Inject
    public UserRepositoryImpl(AppRepository appRepository,
                              ApiRepositoryImpl apiRepositoryImpl) {
        this.apiRepositoryImpl = apiRepositoryImpl;
        this.appRepository = appRepository;
    }

    ApiRepositoryImpl apiRepositoryImpl;

    @Override
    public Completable login(String deviceId, String language, String username, String password) {
        return apiRepositoryImpl.onSignIn(deviceId, language, username, password)
                .flatMapCompletable(new Function<User, CompletableSource>() {
                    @Override
                    public CompletableSource apply(@NotNull User user) throws Exception {
                        appRepository.saveUserName(username);
                        appRepository.savePassword(password);
                        appRepository.saveToken(user.getToken());
                        return Completable.complete();
                    }
                });
    }

    @Override
    public Completable logout() {
        appRepository.saveToken("");
        return Completable.complete();
    }

}
