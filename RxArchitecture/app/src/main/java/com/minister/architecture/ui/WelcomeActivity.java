package com.minister.architecture.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.minister.architecture.MainActivity;
import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportActivity;
import com.minister.architecture.model.bean.GankItemBean;
import com.minister.architecture.ui.gank.GankViewModel;
import com.minister.architecture.util.RxHelp;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 启动页
 * Created by leipe on 2017/9/18.
 * @author leipe
 */
public class WelcomeActivity extends BaseSupportActivity {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    GankViewModel mGankViewModel;
    private Context mContext;

    @BindView(R.id.img_welcome)
    ImageView imgWelcome;
    @BindView(R.id.tv_welcome_author)
    TextView tvWelcomeAuthor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        mGankViewModel = (GankViewModel) setViewModel(GankViewModel.class);
        initView();
    }

    private void initView() {
        mDisposable.add(
                mGankViewModel
                        .getGirlList()
                        .compose(RxHelp.<List<GankItemBean>>rxScheduler())
                        .subscribe(new Consumer<List<GankItemBean>>() {
                            @Override
                            public void accept(@NonNull List<GankItemBean> gankItemBeen) throws Exception {
                                goMainActivity(gankItemBeen.get(0).getUrl());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                goMainActivity("");
                            }
                        }));
    }


    private void goMainActivity(String url) {
        if ("".equals(url)) {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        Glide
                .with(mContext)
                .load(url)
                .into(imgWelcome);
        imgWelcome.animate().scaleX(1.2f).scaleY(1.2f).setDuration(2000).setStartDelay(200).start();
        Flowable
                .timer(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }

    public ViewModel setViewModel(Class className){
        return ViewModelProviders.of(this, mViewModelFactory).get(className);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
