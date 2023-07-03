package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.dbmodels.Email;

import java.util.List;

import io.reactivex.Observable;

public interface EmailLocalRepo {
   Observable<List<Email>> getAll();
   Observable<List<Email>> getByEmail(String key);
   Observable<Boolean> insert(Email email);
}
