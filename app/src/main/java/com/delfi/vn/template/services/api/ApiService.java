package com.delfi.vn.template.services.api;

import com.delfi.vn.template.models.servermodels.User;
import com.delfi.vn.template.models.servermodels.UserAX;
import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Product;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.dbmodels.Receipt11Detail;
import com.delfi.vn.template.models.servermodels.PostMenu11Request;
import com.delfi.vn.template.models.servermodels.base.BaseAXResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {
//    @Headers({"Content-Type:application/x-www-form-urlencoded"})
//    @GET
//    Observable<BaseResponse<User>> onSignIn(@Url String url,
//                                            @QueryMap (encoded =  true) HashMap<String, String > param);

    @FormUrlEncoded
    @POST
    Observable<BaseAXResponse<User>> onSignIn(
            @Url String url, @Field("login") String user, @Field("password") String password);

    //AX Server

    @FormUrlEncoded
    @POST
    Observable<UserAX> onSignInAX(
            @Url String url,
            @Field("client_id") String user,
            @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type,
            @Field("scope") String scope);

    @GET
    Observable<BaseAXResponse<List<Product>>> getProductList(@Url String url);

    @GET
    Observable<BaseAXResponse<List<Warehouse>>> getWarehouseList(@Url String url);

    @GET
    Observable<BaseAXResponse<List<Receipt11>>> getReceipt11(@Url String url);

    @GET
    Observable<BaseAXResponse<List<Receipt11Detail>>> getReceipt11Detail(@Url String url);

    @Headers({"Content-Type:application/json"})
    @POST
    Observable<BaseAXResponse> postMenu11(@Url String url, @Body PostMenu11Request request);
}
