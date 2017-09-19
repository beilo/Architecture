package com.minister.architecture.di.component;

import com.minister.architecture.app.MyApp;
import com.minister.architecture.di.model.ActivityModule;
import com.minister.architecture.di.model.HttpModel;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by leipe on 2017/8/24.
 */

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        HttpModel.class,
        ActivityModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder{ //在这里BindsInstance的东西可以共享,比如MyApp
        @BindsInstance
        Builder app(MyApp app);


        AppComponent build();
    }

    void inject(MyApp app);
}
