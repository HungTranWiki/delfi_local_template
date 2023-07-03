package com.delfi.vn.template.services.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.delfi.vn.template.repositories.AppRepository;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {
    private AppRepository appRepository;

    public AuthenticationInterceptor(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder()
                .addHeader("Accept", "application/json")
                //.addHeader("App-Key", "MUkQhlP5IKMD83yzUC/1qFHhyLCCu7xudxoLsqh41nM")
                .addHeader("User-Agent", "PDA")
                .addHeader("Content-Type", "application/x-www-form-urlencoded");

        String url = request.url().toString().toLowerCase();
        if (url.contains(appRepository.getURLOdoo())) {
            if (!url.contains(Constants.ODOO_LOGIN)) {
                String token = appRepository.getToken();
                builder.addHeader("Access-token", token);
            }
        } else if(Constants.AX_LOGIN != null && !url.contains(Constants.AX_LOGIN) && !url.contains("southeastasia.logic.azure")){
            String token = appRepository.getToken();
            builder.addHeader("Authorization", "Bearer " + token);
        }else if (url.contains(appRepository.getURLPrinter())) {
            Log.d("Printer" , "printer");
        }
        Response response = chain.proceed(builder.build());
        return response;
    }
}
