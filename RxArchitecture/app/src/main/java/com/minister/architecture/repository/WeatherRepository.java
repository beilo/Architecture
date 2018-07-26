package com.minister.architecture.repository;

import com.minister.architecture.model.bean.WeatherBean;
import com.minister.architecture.model.http.WeatherApi;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * 天气Api 数据提供工厂
 * Created by leipe on 2018/2/7.
 */
@Singleton
public class WeatherRepository {

    private WeatherApi mApi;

    @Inject
    public WeatherRepository(WeatherApi mApi) {
        this.mApi = mApi;
    }

    public Flowable<WeatherBean> getWeather(Map map){
        return mApi.getWeather(map);
    }
}
