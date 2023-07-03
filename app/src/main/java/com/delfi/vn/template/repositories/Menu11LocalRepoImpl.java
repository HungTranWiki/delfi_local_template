package com.delfi.vn.template.repositories;

import com.delfi.core.sqlite.QueryOption;
import com.delfi.vn.template.models.appmodels.ProductMenu11;
import com.delfi.vn.template.models.dbmodels.SavedMenu11;

import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class Menu11LocalRepoImpl implements Menu11LocalRepo {
    private LocalRepository localRepo;

    @Inject
    public Menu11LocalRepoImpl(LocalRepository localRepo) {
        this.localRepo = localRepo;
    }

    @Override
    public Observable<List<ProductMenu11>> getProductList(String soCT) {
        String query =
                "SELECT rc.Id, rc.productId , rc.soCT, rc.maVT, rc.missingQty, rc.barcode, rc.configId, rc.sizeId, " +
                        "rc.colorId, rc.styleId, rc.unit, rc.note, rc.status, rc.soLuongYeuCau," +
                        " IFNULL(sum(saved.soLuongDaQuet) ,0) as soLuongDaQuet " +
                        "FROM Receipt11Detail rc " +
                        "LEFT JOIN SavedMenu11 saved on saved.maVT = rc.maVT  AND saved.soCT = rc.soCT " +
                        "where rc.soCT = \""+soCT+"\" " +
                        "Group by rc.Id, rc.productId , rc.soCT, rc.maVT";
        return localRepo.getListByQueryOption(ProductMenu11.class, query);
    }

    @Override
    public Observable<List<SavedMenu11>> getSaved() {
        QueryOption queryOption = new QueryOption();
        return localRepo.getListByQueryOption(SavedMenu11.class, queryOption);
    }

    @Override
    public Observable<List<SavedMenu11>> getSaved(String soCT) {
        LinkedHashMap<String, String> clause = new LinkedHashMap<>();
        clause.put("soCT", soCT);
        String query = new StringBuffer()
                .append("soCT" + " = ? ")
                .toString();
        QueryOption queryOption = new QueryOption(query, clause);
        return localRepo.getListByQueryOption(SavedMenu11.class, queryOption);
    }

    @Override
    public Observable<List<SavedMenu11>> getSaved(int  id) {
        LinkedHashMap<String, String> clause = new LinkedHashMap<>();
        clause.put("Id", id+"");
        String query = new StringBuffer()
                .append("Id" + " = ? ")
                .toString();
        QueryOption queryOption = new QueryOption(query, clause);
        return localRepo.getListByQueryOption(SavedMenu11.class, queryOption);
    }

    @Override
    public Observable<Boolean> save(SavedMenu11 savedInventory) {
        return localRepo.insertBase(savedInventory)
                .map(result -> result > 0);
    }

    @Override
    public Observable<Long> delete() {
        QueryOption queryOption = new QueryOption();
        return localRepo.deleteItems(SavedMenu11.class, queryOption);

    }

    @Override
    public Observable<Long> delete(String soCT) {
        LinkedHashMap<String, String> clause = new LinkedHashMap<>();
        clause.put("soCT", soCT);
        String query = new StringBuffer()
                .append("soCT" + " = ? ")
                .toString();
        QueryOption queryOption = new QueryOption(query, clause);
        return localRepo.deleteItems(SavedMenu11.class, queryOption);

    }

    @Override
    public Observable<Integer> count() {
        return localRepo.count(SavedMenu11.class, new QueryOption());
    }

    @Override
    public Observable<Integer> count(String soCT) {
        LinkedHashMap<String, String> clause = new LinkedHashMap<>();
        clause.put("soCT", soCT);
        String query = new StringBuffer()
                .append("soCT" + " = ? ")
                .toString();
        QueryOption queryOption = new QueryOption(query, clause);
        return localRepo.count(SavedMenu11.class, queryOption);
    }

    @Override
    public Observable<Float> sumSaved(String soCT, String maVT) {
        LinkedHashMap<String, String> clause = new LinkedHashMap<>();
        clause.put("soCT", soCT);
        clause.put("maVT", maVT);
        String query = new StringBuffer()
                .append("soCT" + " = ? and ")
                .append("maVT" + " = ? ")
                .toString();
        QueryOption queryOption = new QueryOption(query, clause);
        return localRepo.getSumOfQuantity(SavedMenu11.class, "soLuongDaQuet", queryOption);
    }


    @Override
    public Observable<List<SavedMenu11>> getProductListGroupByMaVT(String soCT) {
        String query =
                "SELECT rc.Id, rc.soCT , rc.maVT, rc.missingQty, rc.productId , rc.configId, rc.sizeId,\n" +
                        " rc.colorId, rc.styleId, rc.unit ,rc.note, rc.status, rc.tenVT, rc.soLuongYeuCau, " +
                        " rc.barcode, rc.fromWH, rc.toWH, rc.createDate, rc.shipDate, rc.receiveDate,  " +
                        "  IFNULL( sum(rc.soLuongDaQuet) , 0) as soLuongDaQuet  FROM SavedMenu11 rc" +
                        "  where rc.soCT = \""+soCT+"\" " +
                        "Group by rc.soCT, rc.maVT";
        return localRepo.getListByQueryOption(SavedMenu11.class, query);


    }
}
