package com.minister.architecture.ui;

import com.minister.architecture.BuildConfig;
import com.minister.architecture.app.MyApp;
import com.minister.architecture.di.component.AppComponent;
import com.minister.architecture.di.model.HttpModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;


/**
 * Created by leipe on 2017/9/22.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class WelcomeActivityTest {

    @Rule
    public DaggerMockRule<AppComponent> runle = new DaggerMockRule<AppComponent>(AppComponent.class,new HttpModel())
            .set(new DaggerMockRule.ComponentSetter<AppComponent>() {
                @Override
                public void setComponent(AppComponent component) {
                    component.inject((MyApp) RuntimeEnvironment.application);
                }
            });

    public WelcomeActivity mActivity;

//    @Mock
//    GankApi mApi;


    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.setupActivity(WelcomeActivity.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void activityIsNull() {
        ActivityController<WelcomeActivity> welcomeActivityController = Robolectric.buildActivity(WelcomeActivity.class).create().start();
        WelcomeActivity welcomeActivity = welcomeActivityController.get();
        welcomeActivityController.resume();
        Assert.assertEquals("", welcomeActivity.tvWelcomeAuthor.getText());
        Assert.assertNotNull(welcomeActivity.mGankViewModel);
    }

}