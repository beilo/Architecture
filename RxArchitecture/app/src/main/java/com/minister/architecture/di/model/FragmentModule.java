/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.minister.architecture.di.model;

import com.minister.architecture.ui.MainFragment;
import com.minister.architecture.ui.gank.GankTabFragment;
import com.minister.architecture.ui.gank.GirlDetailFragment;
import com.minister.architecture.ui.gank.child.GirlListFragment;
import com.minister.architecture.ui.gank.child.TechListFragment;
import com.minister.architecture.ui.zhihu.ZhiHuDetailFragment;
import com.minister.architecture.ui.zhihu.ZhiHuTabFragment;
import com.minister.architecture.ui.zhihu.child.DailyListFragment;
import com.minister.architecture.ui.zhihu.child.HotListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract MainFragment contributeMainFragment();

    @ContributesAndroidInjector
    abstract TechListFragment contributeRepoTechListFragmentGank();

    @ContributesAndroidInjector
    abstract GirlListFragment contributeRepoGirlListFragment();

    @ContributesAndroidInjector
    abstract GirlDetailFragment contributeGirlDetailFragment();

    @ContributesAndroidInjector
    abstract GankTabFragment contributeGankTabFragment();

    @ContributesAndroidInjector
    abstract ZhiHuTabFragment contributeZhiHuTabFragment();

    @ContributesAndroidInjector
    abstract DailyListFragment contributeDailyListFragment();

    @ContributesAndroidInjector
    abstract ZhiHuDetailFragment contributeDailyDetailFragment();

    @ContributesAndroidInjector
    abstract HotListFragment contributeHotListFragment();
}
