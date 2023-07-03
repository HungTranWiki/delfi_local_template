package com.delfi.vn.template.repositories;

import com.delfi.core.sqlite.QueryOption;
import com.delfi.vn.template.models.dbmodels.Receipt11Detail;

import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class Receipt11LocalRepoImpl implements Receipt11LocalRepo {
    private LocalRepository localRepo;

    @Inject
    public Receipt11LocalRepoImpl(LocalRepository localRepo) {
        this.localRepo = localRepo;
    }


    @Override
    public Observable<Boolean> addNewData(List<Receipt11Detail> list) {
        return localRepo.insertItemList(list, Receipt11Detail.class, 64);
    }

    @Override
    public Observable<List<Receipt11Detail>> getDetail(String soCT) {
        LinkedHashMap<String, String> clause = new LinkedHashMap<>();
        clause.put("soCT", soCT);
        String query = new StringBuffer()
                .append("soCT" + " = ?")
                .toString();
        QueryOption queryOption = new QueryOption(query, clause);
        return localRepo.getListByQueryOption(Receipt11Detail.class, queryOption);
    }

    @Override
    public Observable<List<Receipt11Detail>> getReceipt() {
        QueryOption queryOption = new QueryOption();
        queryOption.groupBy = "soCT";
        queryOption.isDistinct = true;
        return localRepo.getListByQueryOption(Receipt11Detail.class, queryOption);
    }

    @Override
    public Observable<Long> delete() {
        return localRepo.deleteItems(Receipt11Detail.class, new QueryOption());
    }
}
