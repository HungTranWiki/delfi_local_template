package com.delfi.vn.template.services.api;

import com.delfi.vn.template.BuildConfig;
import com.delfi.vn.template.models.servermodels.User;
import com.delfi.vn.template.models.servermodels.UserAX;
import com.delfi.vn.template.models.appmodels.Warehouse;
import com.delfi.vn.template.models.dbmodels.Product;
import com.delfi.vn.template.models.dbmodels.Receipt11;
import com.delfi.vn.template.models.dbmodels.Receipt11Detail;
import com.delfi.vn.template.models.enums.ErrorCode;
import com.delfi.vn.template.models.servermodels.PostMenu11Request;
import com.delfi.vn.template.models.servermodels.base.BaseAXResponse;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.utils.AppException;
import com.delfi.vn.template.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.io.EOFException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public
class ApiRepositoryImpl implements ApiRepository {
    private ApiService apiService;
    private AppRepository appRepository;

    @Inject
    public ApiRepositoryImpl(ApiService apiService, AppRepository appRepository) {
        this.apiService = apiService;
        this.appRepository = appRepository;
    }

    @Override
    public Observable<User> onSignIn(String deviceId, String language, String username, String password) {
        String url = appRepository.getURLOdoo() + "/api/v1/auth/token";
        if (url == null || url.trim().isEmpty()) {
            return Observable.error(new Error(ErrorCode.NO_LOGIN_CONFIGURATION.name()));
        }
        if (username.isEmpty()) {
            return Observable.error(new Error(ErrorCode.NO_USERNAME_INPUT.name()));
        }
        if (password.isEmpty()) {
            return Observable.error(new Error(ErrorCode.NO_PASSWORD_INPUT.name()));
        }
        //String passwordMD5 = toMD5(password);
        return apiService.onSignIn(url, username, password)
                .map(response -> {
                    if (response.status.equalsIgnoreCase("success") && response.data != null) {
                        return response.data;
                    }
                    Constants.showErrorLog(getClass().getName(), new Error("Response Login Error" + response.errorMessages));
                    throw new AppException(ErrorCode.LOGIN_FAIL, 1, response.errorMessages);
                })
                .doOnError(throwable -> {
                    if (throwable instanceof AppException)
                        return;
                    AppException exception = new AppException(ErrorCode.LOGIN_FAIL, throwable);
                    exception.writeAppLog(getClass().getName());
                    throw exception;
                });
    }

    private String toMD5(@NotNull String value) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(value.getBytes(Charset.forName("UTF8")));
            byte[] resultByte = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : resultByte) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public Observable<UserAX> onSignInAX() {
//        UserAX userAX = new UserAX(
//                "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijc3MkI3RkRDNkY3MzlENEI3NkUzOERFMUVEQ0ExQjlGRjQ4MjgxODZSUzI1NiIsInR5cCI6ImF0K2p3dCIsIng1dCI6ImR5dF8zRzl6blV0MjQ0M2g3Y29ibl9TQ2dZWSJ9.eyJuYmYiOjE2ODU0MzI2MjEsImV4cCI6MTY4NTQzNjIyMSwiaXNzIjoiaHR0cHM6Ly9pbnRlZy11YXQudmlldHRpbmhhbmguY29tLnZuOjMyNDQwIiwiYXVkIjpbIk1LRC5BUEkiLCJodHRwczovL2ludGVnLXVhdC52aWV0dGluaGFuaC5jb20udm46MzI0NDAvcmVzb3VyY2VzIl0sImNsaWVudF9pZCI6InBkYV9hcHBfYXBpIiwiY2xpZW50X3JvbGUiOiJta2QiLCJqdGkiOiIyRUVDMjFBQkJBNzdDNTBDNUZENkRGQzdERjIzREU1OSIsImlhdCI6MTY4NTQzMjYyMSwic2NvcGUiOlsibWtkLmFwaSJdfQ.qUWrcnTtKsucCg5Pt_0zjPOEgKd-lDp85L3bMQU5LlzHux6bqSAJtf4K8Z6vaGTQfvLmX_w_Ck4HEzFXtGR8cad3wAqq8EhLt-7KBe9j1aJ8uQTZYkO-lyngP-lwpoC5Rz9JPwlfPm0A-6Rp1rKmSJ5MVT3Qzzo6Ks51_8utWzQq8Lmo5KYVbbI4KQgDLshQcDjQgykMYqwba4hfvpOfgzBlW5LIaFbxLmTKHAsBgiGOnyp0wQfqks3BCTXh0HFzSmSDPxk72avmUf0CmLxLloJ7iMp_Zz0SKODEKlDqOlyriXHqTpGb6-QsgLdwdIJnFLYz5bH59VtdUVR8IGSIBA",
//                "");
//        return Observable.just(userAX);
        String url = appRepository.getURL() + BuildConfig.PORT_AX_LOGIN + com.delfi.vn.template.services.api.Constants.AX_LOGIN;
        String client_id = "pda_app_api";
        String client_secret = "jtYUzFgTrFeQPBDiPG5y0kvheD5S8uat";
        String grant_type = "client_credentials";
        String scope = "mkd.api";

        return apiService.onSignInAX(url, client_id, client_secret, grant_type, scope)
                .map(response -> {
                    if (response != null && !response.getToken().isEmpty()) {
                        return response;
                    }
                    Constants.showErrorLog(getClass().getName(),
                            new Error("Response Login Error" + (response == null ? "token = null" : response.getError())));
                    throw new AppException(ErrorCode.LOGIN_FAIL, 1, (response == null ? "token = null" : response.getError()));
                })
                .doOnError(throwable -> {
                    if (throwable instanceof AppException)
                        return;
                    AppException exception = new AppException(ErrorCode.LOGIN_FAIL, throwable);
                    exception.writeAppLog(getClass().getName());
                    throw exception;
                });
    }

    @Override
    public Observable<List<Product>> getProductList() {
        return Observable.just(DummyData.dummyProductList());
//        String url = appRepository.getURLAX() + BuildConfig.PORT_AX_GET + "api/products/barcodes/all";
//        return apiService.getProductList(url)
//                .map(new Function<BaseAXResponse<List<Product>>, Object>() {
//                    @Override
//                    public Object apply(@NotNull BaseAXResponse<List<Product>> response) throws Exception {
//                        if (response.hasError == false && response.Data != null) {
//                            return response.Data;
//                        }
//                        Constants.showErrorLog(ApiRepositoryImpl.this.getClass().getName(), new Error("Response getProductList Error"
//                                + response.errorMessages + " - " + response.errorMessageList));
//                        throw new AppException(ErrorCode.ERROR_GET_PRODUCT, 1, response.errorMessages + " - " + response.errorMessageList);
//                    }
//                })
//                .doOnError(throwable -> {
//                    if (throwable instanceof AppException)
//                        return;
//                    AppException exception = new AppException(ErrorCode.ERROR_GET_PRODUCT, throwable);
//                    exception.writeAppLog(getClass().getName());
//                    throw exception;
//                });
    }

    @Override
    public Observable<List<Warehouse>> getWarehouseList() {
        return  Observable.just(DummyData.dummyWarehouseList());
//        String url = appRepository.getURLAX() + BuildConfig.PORT_AX_GET + "api/warehouses";
//        return apiService.getWarehouseList(url)
//                .map(new Function<BaseAXResponse<List<Warehouse>>, Object>() {
//                    @Override
//                    public Object apply(@NotNull BaseAXResponse<List<Warehouse>> response) throws Exception {
//                        if (response.hasError == false && response.Data != null) {
//                            return response.Data;
//                        }
//                        Constants.showErrorLog(ApiRepositoryImpl.this.getClass().getName(), new Error("Response getWarehouseList Error"
//                                + response.errorMessages + " - " + response.errorMessageList));
//                        throw new AppException(ErrorCode.GET_WAREHOUSE_ERROR, 1, response.errorMessages + " - " + response.errorMessageList);
//
//                    }
//                })
//                .doOnError(throwable -> {
//                    if (throwable instanceof AppException)
//                        return;
//                    AppException exception = new AppException(ErrorCode.GET_WAREHOUSE_ERROR, throwable);
//                    exception.writeAppLog(getClass().getName());
//                    throw exception;
//                });
    }

    @Override
    public Observable<List<Receipt11>> getReceipt11(String code) {
        return  Observable.just(DummyData.dummyReceipt11());
//        String url = appRepository.getURLAX() + BuildConfig.PORT_AX_GET + "api/orders/" + code + "/ib/3";
//        return apiService.getReceipt11(url)
//                .map(response -> {
//                    if (response.hasError == false && response.Data != null) {
//                        return response.Data;
//                    }
//                    Constants.showErrorLog(getClass().getName(), new Error("Response getReceipt11 Error"
//                            + response.errorMessageList.toString()
//                    ));
//                    throw new AppException(ErrorCode.GET_RECEIPT_11_ERROR, 1, response.errorMessageList.toString());
//                })
//                .doOnError(throwable -> {
//                    if (throwable instanceof AppException)
//                        return;
//                    AppException exception = new AppException(ErrorCode.GET_RECEIPT_11_ERROR, throwable);
//                    exception.writeAppLog(getClass().getName());
//                    throw exception;
//                });
    }

    @Override
    public Observable<List<Receipt11Detail>> getReceipt11Detail(String soCT) {
        return  Observable.just(DummyData.dummyReceipt11Detail());
//
//        String url = appRepository.getURLAX() + BuildConfig.PORT_AX_GET + "api/orderlines/" + soCT + "/lines";
//        return apiService.getReceipt11Detail(url)
//                .map(response -> {
//                    if (response.hasError == false && response.Data != null) {
//                        return response.Data;
//                    }
//                    Constants.showErrorLog(getClass().getName(), new Error("Response getReceipt11Detail Error"
//                            + response.errorMessages + " - " + response.errorMessageList));
//                    throw new AppException(ErrorCode.GET_RECEIPT_11_DETAIL_ERROR, 1, response.errorMessages + " - " + response.errorMessageList);
//                })
//                .doOnError(throwable -> {
//                    if (throwable instanceof AppException)
//                        return;
//                    AppException exception = new AppException(ErrorCode.GET_RECEIPT_11_DETAIL_ERROR, throwable);
//                    exception.writeAppLog(getClass().getName());
//                    throw exception;
//                });
    }


    @Override
    public Observable<Boolean> postMenu11(PostMenu11Request request) {
        String url = BuildConfig.URL_POST_NHAP_KHO;
        return apiService.postMenu11(url, request)
                .map(new Function<BaseAXResponse, Boolean>() {
                    @Override
                    public Boolean apply(@NotNull BaseAXResponse response) throws Exception {
                        if (response.status.equalsIgnoreCase("success"))
                            return true;
                        AppException exception = new AppException(ErrorCode.SYNC_MENU_11, 1, response.errorMessages);
                        exception.writeAppLog(ApiRepositoryImpl.this.getClass().getName());
                        throw exception;

                    }
                })
                .onErrorReturn(throwable -> {
                    if (throwable instanceof EOFException) {
                        return true;
                    }
                    return false;
                });
    }


}
