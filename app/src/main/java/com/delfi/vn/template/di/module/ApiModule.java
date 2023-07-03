
package com.delfi.vn.template.di.module;

import android.app.Application;
import android.content.Context;

import com.delfi.core.common.SharedManager;
import com.delfi.vn.template.services.api.AuthenticationInterceptor;
import com.delfi.vn.template.services.api.NetworkInterceptor;
import com.delfi.vn.template.services.api.ApiService;
import com.delfi.vn.template.repositories.AppRepository;
import com.delfi.vn.template.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
    @Provides
    @Singleton
    GsonBuilder provideGSonBuilder() {
        return new GsonBuilder();
    }

    @Provides
    @Singleton
    Cache provideCache(@NotNull Application application) {
        long cacheSize = 10 * 1024 * 1024; // 10 MB
        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        return new Cache(httpCacheDirectory, cacheSize);
    }

    @Provides
    @Singleton
    AuthenticationInterceptor provideAuthenticationInterceptor(AppRepository appRepository) {
        return new AuthenticationInterceptor(appRepository);
    }

    @Provides
    @Singleton
    NetworkInterceptor provideNetworkInterceptor(Context context) {
        return new NetworkInterceptor(context);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, AuthenticationInterceptor authInterceptor, NetworkInterceptor networkInterceptor) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache);
        client.connectTimeout(10, TimeUnit.MINUTES);
        client.writeTimeout(10, TimeUnit.MINUTES);
        client.readTimeout(10, TimeUnit.MINUTES);
        client.retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool(0, 1, TimeUnit.NANOSECONDS));

        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            client.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            client.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        client.addInterceptor(authInterceptor);
        client.addInterceptor(networkInterceptor);
        client.addInterceptor(logging);
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Context context, @NotNull GsonBuilder gsonBuilder, OkHttpClient client) {
        String url = SharedManager.getInstance(context).getString(Constants.URL);
        if(url.isEmpty())
            url = Constants.BASE_URL;

        Gson gson = gsonBuilder.serializeNulls()
                .setLenient().create();
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(url)
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(@NotNull Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
