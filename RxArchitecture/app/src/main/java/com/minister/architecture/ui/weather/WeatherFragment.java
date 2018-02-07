package com.minister.architecture.ui.weather;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportFragment;
import com.minister.architecture.model.bean.WeatherBean;
import com.minister.architecture.util.RxHelp;
import com.minister.architecture.viewmodel.WeatherViewModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subscribers.DefaultSubscriber;

/**
 * @author 被咯苏州
 *         Created by leipe on 2018/2/7.
 */

public class WeatherFragment extends BaseSupportFragment {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_nowTemperature)
    TextView tvNowTemperature;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.img_weatherIco)
    ImageView imgWeatherIco;
    @BindView(R.id.cl_weather_top)
    ConstraintLayout clWeatherTop;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private void fillView(WeatherBean weatherBean) {
        if (weatherBean != null) {
            Glide.with(_mActivity).load(weatherBean.getWeatherIco()).into(imgWeatherIco);

            setToolbar(toolbar, weatherBean.getCityName(), 0);
            tvDate.setText(weatherBean.getDate());
            tvNowTemperature.setText(weatherBean.getNowTemperature() + " ℃");
            tvWeather.setText(weatherBean.getWeather());
            tvTemperature.setText(weatherBean.getTemperature());
        }
    }

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    WeatherViewModel mViewModel;

    public static WeatherFragment newInstance() {
        Bundle args = new Bundle();
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_weather_home, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(WeatherViewModel.class);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        refresh.setRefreshing(true);
        loadData();
    }

    private void loadData() {
        Map<String, String> map = new HashMap<>(1);
        map.put("city", "huangshan");
        mViewModel.getWeather(map)
                .compose(RxHelp.<WeatherBean>rxScheduler())
                .subscribe(new DefaultSubscriber<WeatherBean>() {
                    @Override
                    public void onNext(WeatherBean weatherBean) {
                        fillView(weatherBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(_mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                        stopRefresh();
                    }

                    @Override
                    public void onComplete() {
                        stopRefresh();
                    }
                });
    }

    private void stopRefresh() {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
    }
}
