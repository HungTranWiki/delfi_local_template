package com.delfi.vn.template.repositories;

import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Product;
import com.delfi.vn.template.services.api.ApiRepositoryImpl;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GeneralRepoImpl implements GeneralRepo {
    private ApiRepositoryImpl apiRepositoryImpl;
    private AppRepository appRepository;
    private WarehouseLocalRepo warehouseLocalRepo;
    private ProductLocalRepo productLocalRepo;

    @Inject
    public GeneralRepoImpl(ApiRepositoryImpl apiRepositoryImpl,
                           AppRepository appRepository,
                           WarehouseLocalRepo warehouseLocalRepo,
                           ProductLocalRepo productLocalRepo) {
        this.apiRepositoryImpl = apiRepositoryImpl;
        this.appRepository = appRepository;
        this.warehouseLocalRepo = warehouseLocalRepo;
        this.productLocalRepo = productLocalRepo;
    }

    @Override
    public Observable<List<Warehouse>> getWarehouseListFromLogin() {
        return warehouseLocalRepo.getAll();
    }

    @Override
    public Observable<List<Warehouse>> getWarehouseList() {
        return apiRepositoryImpl.getWarehouseList();
    }


    @Override
    public Observable<Boolean> checkExistedProduct() {
        return productLocalRepo.counter()
                .map(counter -> counter > 0);
    }

    @Override
    public Observable<Boolean> loginAX() {
        return apiRepositoryImpl.onSignInAX()
                .map(result -> {
                    appRepository.saveToken(result.getToken());
                    return true;
                });
    }

    @Override
    public Observable<Boolean> getProductListFromSoanHang() {
        return productLocalRepo.delete()
                .flatMap(result -> apiRepositoryImpl.getProductList())
               .flatMap(list -> productLocalRepo.addNewData(list));

//        return apiRepositoryImpl.onSignInAX()
//                .flatMap(result -> {
//                    appRepository.saveToken(result.getToken());
//                    return productLocalRepo.delete();
//                })
//                .flatMap(result -> apiRepositoryImpl.getProductList())
//                .flatMap(list -> productLocalRepo.addNewData(list));
    }

    @Override
    public Observable<List<Product>> getProductList(String maVT) {
        return productLocalRepo.getProduct(maVT);
    }

}