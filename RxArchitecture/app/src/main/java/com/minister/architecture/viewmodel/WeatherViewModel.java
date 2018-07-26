package com.minister.architecture.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.minister.architecture.app.MyApp;
import com.minister.architecture.model.bean.DaoSession;
import com.minister.architecture.model.bean.WeatherBean;
import com.minister.architecture.repository.WeatherRepository;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by leipe on 2018/2/7.
 */

public class WeatherViewModel extends ViewModel {

    private WeatherRepository mRepository;
    private DaoSession mDaoSession;

    public WeatherViewModel() {
        this.mRepository = WeatherRepository.getInstall();
        this.mDaoSession = MyApp.getInstance().getDaoSession();
    }

    public Flowable<WeatherBean> getWeather(String city){
        Map<String, String> map = new HashMap<>(1);
        map.put("city", city);
        return mRepository.getWeather(map);
    }

    public Flowable<String> getBroadcastWeather(String city){
        Map<String, String> map = new HashMap<>(1);
        map.put("city", city);
        return mRepository.getWeather(map)
                .map(new Function<WeatherBean, String>() {
                    @Override
                    public String apply(WeatherBean weatherBean) throws Exception {
                        if (weatherBean!=null){
                            return weatherBean.toString();
                        }else {
                            return "数据为空";
                        }
                    }
                });
    }
}
