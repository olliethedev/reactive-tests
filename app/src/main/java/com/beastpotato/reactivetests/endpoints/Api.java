package com.beastpotato.reactivetests.endpoints;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oleksiy on 3/19/2017.
 */

public class Api {
    protected Retrofit mRetrofit;
    protected EndpointInterface mApiService;
    private static Api instance;

    private Api() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())) //async
                .build();
        mApiService =
                mRetrofit.create(EndpointInterface.class);
    }

    public static Api getInstance() {
        if (instance == null)
            instance = new Api();
        return instance;
    }

    public EndpointInterface getService() {
        return mApiService;
    }
}
