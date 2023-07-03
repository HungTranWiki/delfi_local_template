package com.delfi.vn.template.repositories;

import com.delfi.core.sqlite.QueryOption;
import com.delfi.vn.template.models.dbmodels.Phone;

import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PhoneLocalRepoImpl implements PhoneLocalRepo {
    private LocalRepository localRepo;

    @Inject
    public PhoneLocalRepoImpl(LocalRepository localRepo) {
        this.localRepo = localRepo;
    }

    @Override
    public Observable<List<Phone>> getAll() {
        return localRepo.getListByQueryOption(Phone.class, new QueryOption());
    }

    @Override
    public Observable<List<Phone>> getByPhone(String key) {
        LinkedHashMap<String, String> clause = new LinkedHashMap<>();
        clause.put("phoneNumber", key);
        String query = new StringBuffer()
                .append("phoneNumber = ?")
                .toString();
        QueryOption queryOption = new QueryOption(query, clause);
        return localRepo.getListByQueryOption(Phone.class, queryOption);

    }

    @Override
    public Observable<Boolean> insert(Phone Phone) {
        return localRepo.insertBase(Phone)
                .map(result -> result > 0);
    }
}