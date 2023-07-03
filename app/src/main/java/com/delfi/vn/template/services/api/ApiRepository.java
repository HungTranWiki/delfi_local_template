package com.delfi.vn.template.services.api;

import com.delfi.vn.template.models.servermodels.User;
import com.delfi.vn.template.models.servermodels.UserAX;
import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Product;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.dbmodels.Receipt11Detail;
import com.delfi.vn.template.models.servermodels.PostMenu11Request;

import java.util.List;

import io.reactivex.Observable;

public interface ApiRepository {
    //Server
    Observable<User> onSignIn(String deviceId, String language, String username, String password);

    Observable<List<Receipt11>> getReceipt11(String fromDate);

    Observable<List<Receipt11Detail>> getReceipt11Detail(String soCT);

    Observable<Boolean> postMenu11(PostMenu11Request request);

    Observable<List<Warehouse>> getWarehouseList();
    //AX Server
    Observable<UserAX> onSignInAX();

    Observable<List<Product>> getProductList();

}
