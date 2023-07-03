package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.dbmodels.Phone;

import java.util.List;

import io.reactivex.Observable;

public interface PhoneRepo {
   Observable<List<Phone>> getAll();
   Observable<Boolean> checkExisted(String key);
   Observable<Boolean> insert(String phoneNumber);
}
