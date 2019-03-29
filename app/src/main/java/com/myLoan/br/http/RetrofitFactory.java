package com.myLoan.br.http;

import com.myLoan.br.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new TokenHeadInterceptor())
            .readTimeout(12000, TimeUnit.SECONDS)
            .writeTimeout(12000,TimeUnit.SECONDS)
            .connectTimeout(12000, TimeUnit.SECONDS)
            .build();
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonUtil.buildGosn()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

    /**
     * 创建retrofit
     *
     * @return
     */
    public static Retrofit createFactoty() {
        return retrofit;
    }

}