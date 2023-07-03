package com.delfi.vn.template.repositories;

import io.reactivex.Completable;

public interface UserRepository {
    Completable login(String deviceId, String language, String username, String password);
    Completable logout();
}