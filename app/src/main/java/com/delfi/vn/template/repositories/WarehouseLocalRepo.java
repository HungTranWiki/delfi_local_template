package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.appmodels.Warehouse;

import java.util.List;

import io.reactivex.Observable;

public interface WarehouseLocalRepo {
    Observable<List<Warehouse>> getAll();
    Observable<Boolean> addNewData(List<Warehouse> list);
    Observable<Long> delete();
}
