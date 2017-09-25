package com.minister.architecture.di.model;

import android.content.Context;
import android.net.ConnectivityManager;

import com.minister.architecture.BuildConfig;
import com.minister.architecture.app.MyApp;
import com.minister.architecture.model.http.GankApi;
import com.minister.architecture.model.http.WeChatApis;
import com.minister.architecture.model.http.ZhihuApis;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/** HTTP 请求model,包含了ViewModel
 * Created by leipe on 2017/8/24.
 */

@Module(includes = ViewModelModule.class)
public class HttpModel {

    @Singleton
    @Provides
    public GankApi providesGankApi(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, GankApi.HOST).create(GankApi.class);
    }


    @Singleton
    @Provides
    public WeChatApis providesWeChatApis(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, WeChatApis.HOST).create(WeChatApis.class);
    }

    @Singleton
    @Provides
    public ZhihuApis providesZhihuApis(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ZhihuApis.HOST).create(ZhihuApis.class);
    }

    @Singleton
    @Provides
    public OkHttpClient providesOkHttpClient(OkHttpClient.Builder builder, final MyApp app) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Logger.t("OkHttp").d(message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(app.getCacheDir().getAbsolutePath() + File.separator + "data" + "/NetCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!isNetworkConnected(app)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (isNetworkConnected(app)) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    @Singleton
    @Provides
    public Retrofit.Builder providesRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    public OkHttpClient.Builder providesOkHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public boolean isNetworkConnected(MyApp app) {
        ConnectivityManager connectivityManager = (ConnectivityManager) app.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

}
