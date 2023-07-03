package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Product;

import java.util.List;

import io.reactivex.Observable;

public interface GeneralRepo {

    Observable<Boolean> checkExistedProduct();

    Observable<Boolean> getProductListFromSoanHang();

    Observable<List<Product>> getProductList(String maVT);

    Observable<List<Warehouse>> getWarehouseListFromLogin();

    Observable<List<Warehouse>> getWarehouseList();

    Observable<Boolean> loginAX();

}
