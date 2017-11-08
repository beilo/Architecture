package com.minister.architecture.di.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.minister.architecture.di.ViewModelKey;
import com.minister.architecture.viewmodel.GankViewModel;
import com.minister.architecture.viewmodel.ZhiHuViewModel;
import com.minister.architecture.viewmodel.GithubViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GankViewModel.class)
    abstract ViewModel bindGankViewModel(GankViewModel gankViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ZhiHuViewModel.class)
    abstract ViewModel bindZhihuViewModel(ZhiHuViewModel zhiHuViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(GithubViewModelFactory factory);
}
