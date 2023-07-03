package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.dbmodels.Phone;

import java.util.List;

import io.reactivex.Observable;

public interface PhoneLocalRepo {
   Observable<List<Phone>> getAll();
   Observable<List<Phone>> getByPhone(String key);
   Observable<Boolean> insert(Phone Phone);
}
