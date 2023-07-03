package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.appmodels.ProductMenu11;
import com.delfi.vn.template.models.dbmodels.SavedMenu11;

import java.util.List;

import io.reactivex.Observable;

public interface Menu11Repo {
    Observable<List<ProductMenu11>> getProductList(String soCT);

    Observable<Boolean> postMenu11(String soCt);

    Observable<Boolean> save(SavedMenu11 ct);

    Observable<Integer> counterSaved(String soCt);

    Observable<Integer> counterSaved();

    Observable<Float> sumSaved(String soCT, String maVT);

    Observable<Boolean> delete(String soCt);

    Observable<Boolean> delete();

    Observable<List<SavedMenu11>> exportOutput();

    Observable<Integer> prepareMasterData();

}
