package com.delfi.vn.template.repositories;

import com.delfi.core.sqlite.QueryOption;
import com.delfi.vn.template.models.dbmodels.Email;

import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class EmailLocalRepoImpl implements EmailLocalRepo {
    private LocalRepository localRepo;

    @Inject
    public EmailLocalRepoImpl(LocalRepository localRepo) {
        this.localRepo = localRepo;
    }

    @Override
    public Observable<List<Email>> getAll() {
        return localRepo.getListByQueryOption(Email.class, new QueryOption());
    }

    @Override
    public Observable<List<Email>> getByEmail(String key) {
        LinkedHashMap<String, String> clause = new LinkedHashMap<>();
        clause.put("email", key);
        String query = new StringBuffer()
                .append("email = ?")
                .toString();
        QueryOption queryOption = new QueryOption(query, clause);
        return localRepo.getListByQueryOption(Email.class, queryOption);

    }

    @Override
    public Observable<Boolean> insert(Email email) {
        return localRepo.insertBase(email)
                .map(result -> result > 0);
    }
}