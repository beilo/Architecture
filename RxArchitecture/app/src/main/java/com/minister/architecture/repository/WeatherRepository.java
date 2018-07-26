package com.minister.architecture.repository;

import com.lmroom.baselib.http.RetrofitUtil;
import com.minister.architecture.model.bean.WeatherBean;
import com.minister.architecture.model.http.WeatherApi;

import java.util.Map;

import io.reactivex.Flowable;

/**
 * 天气Api 数据提供工厂
 * Created by leipe on 2018/2/7.
 */

public class WeatherRepository {

    private static class WeatherHolder {
        private static final WeatherRepository INSTALL = new WeatherRepository();
    }

    private WeatherApi mApi;

    private WeatherRepository() {

        this.mApi = RetrofitUtil.getInstall().getRetrofit(WeatherApi.HOST)
                .create(WeatherApi.class);
    }

    /**
     * Singleton install
     * @return WeatherRepository
     */
    public static final WeatherRepository getInstall() {
        return WeatherHolder.INSTALL;
    }

    public Flowable<WeatherBean> getWeather(Map map) {
        return mApi.getWeather(map);
    }
}
