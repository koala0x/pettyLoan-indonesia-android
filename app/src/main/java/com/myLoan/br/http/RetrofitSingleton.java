package com.myLoan.br.http;

import com.myLoan.br.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private RetrofitSingleton() {
    }

    private Retrofit retrofit;
    private static volatile RetrofitSingleton instance = null;

    public static RetrofitSingleton getInstance() {
        if (instance == null) {
            synchronized (RetrofitSingleton.class) {
                if (instance == null) {
                    instance = new RetrofitSingleton();
                }
            }
        }
        return instance;
    }
    public synchronized Retrofit getRetrofit() {
        return getRetrofit(10,10);
    }

    public synchronized Retrofit getRetrofit(int readSecond,int writeSecond) {
        if (retrofit == null) {
//            File httpCacheDirectory = new File(MyApplication.getMyApplication().getCacheDir(), "responses");

//            int cacheSize = 10 * 1024 * 1024; // 10 MiB
//            Cache cache = new Cache(httpCacheDirectory, cacheSize);
//            Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    CacheControl.Builder cacheBuilder = new CacheControl.Builder();
//                    cacheBuilder.maxAge(0, TimeUnit.SECONDS);
//                    cacheBuilder.maxStale(365, TimeUnit.DAYS);
//                    CacheControl cacheControl = cacheBuilder.build();
//                    Request request = chain.request();
//                    if (!NetWorkUtil.isNetworkConnected()) {
//                        request = request.newBuilder()
//                                .cacheControl(cacheControl)
//                                .build();
//                    }
//                    Response originalResponse = chain.proceed(request);
//                    if (NetWorkUtil.isNetworkConnected()) {
//                        int maxAge = 0; // read from cache
//                        return originalResponse.newBuilder()
//                                .removeHeader("Pragma")
//                                .header("Cache-Control", "public ,max-age=" + maxAge)
//                                .build();
//                    } else {
//                        int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//                        return originalResponse.newBuilder()
//                                .removeHeader("Pragma")
//                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                                .build();
//                    }
//                }
//            };

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new TokenHeadInterceptor())
//                    .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//                    .cache(cache)
                    .readTimeout(readSecond, TimeUnit.SECONDS)
                    .writeTimeout(writeSecond, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create(GsonUtil.buildGosn()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        }

        return retrofit;
    }
}