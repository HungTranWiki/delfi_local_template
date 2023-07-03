package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.dbmodels.Receipt11Detail;

import java.util.List;

import io.reactivex.Observable;

public interface Receipt11Repo {
    Observable<List<Receipt11>> pullReceiptListFromServer();

    Observable<List<Receipt11>> pullReceiptListFromServer(String code);

    Observable<List<Receipt11Detail>> getDetail(String soCT);

}
