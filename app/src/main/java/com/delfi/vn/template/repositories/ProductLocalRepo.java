package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.dbmodels.Product;

import java.util.List;

import io.reactivex.Observable;

public interface ProductLocalRepo {
    Observable<List<Product>> getProduct();

    Observable<List<Product>> getProductByBarcode(String barcode);

    Observable<List<Product>> getProduct(String maVT);

    Observable<Boolean> addNewData(List<Product> list);

    Observable<Long> delete();

    Observable<Integer> counter();
}
