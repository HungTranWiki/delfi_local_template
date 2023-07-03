package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.dbmodels.Phone;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PhoneRepoImpl implements PhoneRepo {
    private PhoneLocalRepo phoneLocalRepo;

    @Inject
    public PhoneRepoImpl(PhoneLocalRepo phoneLocalRepo) {
        this.phoneLocalRepo = phoneLocalRepo;
    }

    @Override
    public Observable<List<Phone>> getAll() {
        return phoneLocalRepo.getAll();
    }

    @Override
    public Observable<Boolean> checkExisted(String key) {
        return phoneLocalRepo.getByPhone(key)
                .map(list -> (list.size() >= 1));
    }

    @Override
    public Observable<Boolean> insert(String phoneNumber) {
        return phoneLocalRepo.getByPhone(phoneNumber)
                .flatMap(list -> {
                    if (list.size() > 0) {
                        return Observable.just(false);
                    }
                    return phoneLocalRepo.insert(new Phone(phoneNumber));

                });
    }

}