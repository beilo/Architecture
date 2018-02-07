package com.minister.architecture.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.minister.architecture.model.bean.DaoSession;
import com.minister.architecture.model.bean.WeatherBean;
import com.minister.architecture.repository.WeatherRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by leipe on 2018/2/7.
 */

public class WeatherViewModel extends ViewModel {

    private WeatherRepository mRepository;
    private DaoSession mDaoSession;

    @Inject
    public WeatherViewModel(WeatherRepository mRepository, DaoSession mDaoSession) {
        this.mRepository = mRepository;
        this.mDaoSession = mDaoSession;
    }

    public Flowable<WeatherBean> getWeather(Map map){
        return mRepository.getWeather(map);
    }
}
