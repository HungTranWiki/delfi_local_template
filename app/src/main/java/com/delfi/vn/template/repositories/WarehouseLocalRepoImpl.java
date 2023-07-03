package com.delfi.vn.template.repositories;

import com.delfi.core.sqlite.QueryOption;
import com.delfi.vn.template.models.appmodels.Warehouse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WarehouseLocalRepoImpl implements WarehouseLocalRepo {
    private LocalRepository localRepo;

    @Inject
    public WarehouseLocalRepoImpl(LocalRepository localRepo) {
        this.localRepo = localRepo;
    }


    @Override
    public Observable<List<Warehouse>> getAll() {
        return localRepo.getListByQueryOption(Warehouse.class, new QueryOption());
    }

    @Override
    public Observable<Boolean> addNewData(List<Warehouse> list) {
      return localRepo.insertItemList(list, Warehouse.class, 400);
    }

    @Override
    public Observable<Long> delete() {
        return localRepo.deleteItems(Warehouse.class, new QueryOption());
    }
}
