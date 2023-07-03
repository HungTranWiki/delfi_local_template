package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.dbmodels.Email;

import java.util.List;

import io.reactivex.Observable;

public interface EmailRepo {
   Observable<List<Email>> getAll();
   Observable<Boolean> checkExisted(String key);
   Observable<Boolean> insert(String email);
}
