package com.minister.architecture.model.http;

import com.minister.architecture.model.bean.WeatherBean;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by leipe on 2018/2/7.
 */

public interface WeatherApi {
    String HOST = "http://172.20.16.26:8089/";

    @Headers({
            "Connection:keep-alive",
            "Content-Type:application/json"
    })
    @POST("getWeather")
    Flowable<WeatherBean> getWeather(@Body Map map);
}
