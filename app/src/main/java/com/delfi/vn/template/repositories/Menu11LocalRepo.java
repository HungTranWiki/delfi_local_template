package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.appmodels.ProductMenu11;
import com.delfi.vn.template.models.dbmodels.SavedMenu11;

import java.util.List;

import io.reactivex.Observable;

public interface Menu11LocalRepo {
    Observable<List<ProductMenu11>>getProductList(String soCT);

    Observable<List<SavedMenu11>> getSaved();

    Observable<List<SavedMenu11>> getSaved(String soCT);

    Observable<List<SavedMenu11>> getSaved(int id);

    Observable<Boolean> save(SavedMenu11 savedInventory);
    
    Observable<Long> delete();

    Observable<Long> delete(String soCT);

    Observable<Integer> count();

    Observable<Integer> count(String soCT);

    Observable<Float> sumSaved(String soCT, String maVT);

    Observable<List<SavedMenu11>> getProductListGroupByMaVT(String soCT);

}
