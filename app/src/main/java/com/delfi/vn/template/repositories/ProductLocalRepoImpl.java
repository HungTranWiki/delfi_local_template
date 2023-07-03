package com.delfi.vn.template.repositories;

import com.delfi.core.sqlite.QueryOption;
import com.delfi.vn.template.models.dbmodels.Product;

import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ProductLocalRepoImpl implements ProductLocalRepo {
    private LocalRepository localRepo;

    @Inject
    public ProductLocalRepoImpl(LocalRepository localRepo) {
        this.localRepo = localRepo;
    }


    @Override
    public Observable<Boolean> addNewData(List<Product> list) {
        return localRepo.insertItemList(list, Product.class, 30);
    }

    @Override
    public Observable<List<Product>> getProductByBarcode(String barcode) {
        LinkedHashMap<String, String> clause = new LinkedHashMap<>();
        clause.put("barcode", barcode);
        String query = new StringBuffer()
                .append("barcode" + " = ?")
                .toString();
        QueryOption queryOption = new QueryOption(query, clause);
        return localRepo.getListByQueryOption(Product.class, queryOption);
    }

    @Override
    public Observable<List<Product>> getProduct(String maVT) {
        LinkedHashMap<String, String> clause = new LinkedHashMap<>();
        clause.put("productId", maVT);
        String query = new StringBuffer()
                .append("productId" + " = ?")
                .toString();
        QueryOption queryOption = new QueryOption(query, clause);
        return localRepo.getListByQueryOption(Product.class, queryOption);
    }

    @Override
    public Observable<List<Product>> getProduct() {
        QueryOption queryOption = new QueryOption();
        queryOption.groupBy = "productId";
        queryOption.isDistinct = true;
        return localRepo.getListByQueryOption(Product.class, queryOption);
    }

    @Override
    public Observable<Long> delete() {
        return localRepo.deleteItems(Product.class, new QueryOption());
    }

    @Override
    public Observable<Integer> counter() {
        return localRepo.countAllItems(Product.class);
    }
}
