package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.dbmodels.Email;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class EmailRepoImpl implements EmailRepo {
    private EmailLocalRepo emailLocalRepo;

    @Inject
    public EmailRepoImpl(EmailLocalRepo emailLocalRepo) {
        this.emailLocalRepo = emailLocalRepo;
    }

    @Override
    public Observable<List<Email>> getAll() {
        return emailLocalRepo.getAll();
    }

    @Override
    public Observable<Boolean> checkExisted(String key) {
        return emailLocalRepo.getByEmail(key)
                .map(list -> (list.size() >= 1));
    }

    @Override
    public Observable<Boolean> insert(String email) {
        return emailLocalRepo.getByEmail(email)
                .flatMap(list -> {
                    if (list.size() > 0) {
                        return Observable.just(false);
                    }
                    return emailLocalRepo.insert(new Email(email));

                });
    }
}