package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.dbmodels.Receipt11Detail;
import com.delfi.vn.template.services.api.ApiRepositoryImpl;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class Receipt11RepoImpl implements Receipt11Repo {
    private ApiRepositoryImpl apiRepositoryImpl;
    private Receipt11LocalRepo receipt11LocalRepo;

    @Inject
    public Receipt11RepoImpl(ApiRepositoryImpl apiRepositoryImpl,
                             Receipt11LocalRepo receipt11LocalRepo) {
        this.apiRepositoryImpl = apiRepositoryImpl;
        this.receipt11LocalRepo = receipt11LocalRepo;
    }

    @Override
    public Observable<List<Receipt11>> pullReceiptListFromServer() {
        return apiRepositoryImpl.getReceipt11("");
    }

    @Override
    public Observable<List<Receipt11>> pullReceiptListFromServer(String code) {
        return apiRepositoryImpl.getReceipt11(code);
    }

    @Override
    public Observable<List<Receipt11Detail>> getDetail(String soCT) {
        return receipt11LocalRepo.delete()
                     .flatMap(result -> apiRepositoryImpl.getReceipt11Detail(soCT))
            .flatMap(list -> receipt11LocalRepo.addNewData(list))
            .flatMap(result -> receipt11LocalRepo.getReceipt());
    }
//
//     return receipt11LocalRepo.delete()
//             .flatMap(result -> apiRepository.get11(fromDate))
//            .flatMap(list -> receipt11LocalRepo.addNewData(list))
//            .flatMap(result -> receipt11LocalRepo.getReceipt());
}
