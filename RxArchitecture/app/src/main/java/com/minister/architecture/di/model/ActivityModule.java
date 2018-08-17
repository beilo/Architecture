package com.minister.architecture.di.model;


import com.minister.architecture.MainActivity;
import com.minister.architecture.ui.WelcomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by leipe on 2017/8/24.
 */

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract WelcomeActivity contributeWelcomeActivity();

}
