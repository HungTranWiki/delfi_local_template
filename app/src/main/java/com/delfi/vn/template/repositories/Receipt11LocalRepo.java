package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.dbmodels.Receipt11Detail;

import java.util.List;

import io.reactivex.Observable;

public interface Receipt11LocalRepo {
    Observable<List<Receipt11Detail>> getReceipt();
    Observable<List<Receipt11Detail>> getDetail(String soCT);
    Observable<Boolean> addNewData(List<Receipt11Detail> list);
    Observable<Long> delete();
}
