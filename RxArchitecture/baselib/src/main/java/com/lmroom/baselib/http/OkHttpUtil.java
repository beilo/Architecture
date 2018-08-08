package com.lmroom.baselib.http;

import android.os.Environment;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtil {
    private static class OkHttpHolder {
        private static final OkHttpUtil INSTALL = new OkHttpUtil();
    }

    private OkHttpUtil() {
    }

    private OkHttpClient.Builder builder;

    public static final OkHttpUtil getInstall() {
        return OkHttpHolder.INSTALL;
    }

    OkHttpClient getOkHttpClient() {
        if (builder == null) {
            builder = new OkHttpClient.Builder();
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        File cacheFile = new File(Environment.getDataDirectory().getPath() + File.separator + "data" + "/NetCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        //        Interceptor cacheInterceptor = new Interceptor() {
        //            @Override
        //            public Response intercept(Chain chain) throws IOException {
        //                Request request = chain.request();
        //                if (!isNetworkConnected(app)) {
        //                    request = request.newBuilder()
        //                            .cacheControl(CacheControl.FORCE_CACHE)
        //                            .build();
        //                }
        //                Response response = chain.proceed(request);
        //                if (isNetworkConnected(app)) {
        //                    int maxAge = 0;
        //                    // 有网络时, 不缓存, 最大保存时长为0
        //                    response.newBuilder()
        //                            .header("Cache-Control", "public, max-age=" + maxAge)
        //                            .removeHeader("Pragma")
        //                            .build();
        //                } else {
        //                    // 无网络时，设置超时为4周
        //                    int maxStale = 60 * 60 * 24 * 28;
        //                    response.newBuilder()
        //                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
        //                            .removeHeader("Pragma")
        //                            .build();
        //                }
        //                return response;
        //            }
        //        };
        //设置缓存
        builder.cache(cache);
        builder.addNetworkInterceptor(new StethoInterceptor());
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }
}
