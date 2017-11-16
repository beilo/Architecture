package com.minister.architecture.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.minister.architecture.BuildConfig;
import com.minister.architecture.app.MyApp;
import com.minister.architecture.model.bean.DaoMaster;
import com.minister.architecture.model.bean.DaoSession;
import com.minister.architecture.model.bean.GankItemBean;
import com.minister.architecture.model.http.GankApi;
import com.minister.architecture.model.http.result.GankHttpResponse;
import com.minister.architecture.util.NetWorkUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.powermock.reflect.Whitebox;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * @author leipe
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({NetWorkUtils.class})
public class GankRepositoryTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private GankApi api;
    private GankRepository repository;

    Context context;
    MyApp app;

    DaoSession dao;

    private GankHttpResponse<List<GankItemBean>> gankHttpResponse;
    private List<GankItemBean> list;

    @Before
    public void setup() {
        api = mock(GankApi.class);
        context = mock(Context.class);
        app = mock(MyApp.class);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(RuntimeEnvironment.application, null, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        dao = daoMaster.newSession();

        repository = new GankRepository(api, dao);

        // 初始化数据
        list = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("http://img.gank.io/3b0b193d-6abf-4714-9d5a-5508404666f4");
        list.add(
                new GankItemBean(
                        "59b667cf421aa9118887ac12",
                        "2017-09-11T18:39:11.631Z",
                        "用少量Rxjava代码，为retrofit添加退避重试功能",
                        "2017-09-12T08:15:08.26Z",
                        "web",
                        "Android",
                        "http://www.jianshu.com/p/fca90d0da2b5",
                        true,
                        "小鄧子",
                        strings,
                        0
                )
        );
        gankHttpResponse = new GankHttpResponse<>(false, list);
    }


    @Test
    public void getTechList() throws Exception {
        repository.getTechList("", 10, 1);
        verify(api, times(1)).getTechList("", 10, 1);
    }

    @Test
    public void getGirlList() throws Exception {
        mockStatic(NetWorkUtils.class);
        Whitebox.setInternalState(repository, "app", app);
        when(NetWorkUtils.isNetworkConnected(app)).thenReturn(true);

        when(repository.getGirlList(10, 1)).thenReturn(Flowable.just(gankHttpResponse));

        Flowable<GankHttpResponse<List<GankItemBean>>> girlList = repository.getGirlList(10, 1);

        TestSubscriber<GankHttpResponse<List<GankItemBean>>> testSubscriber = new TestSubscriber<>();
        girlList
                .subscribe(testSubscriber);
        testSubscriber.assertNoErrors();

        verify(api, times(1)).getGirlList(10, 1);

        // 返回没有网络
        when(NetWorkUtils.isNetworkConnected(app)).thenReturn(false);

        whenNew(GankHttpResponse.class).withArguments(anyBoolean(),any(List.class)).thenReturn(gankHttpResponse);
        Flowable<GankHttpResponse<List<GankItemBean>>> daoGirlList = repository.getGirlList(10, 1);

        TestSubscriber<GankHttpResponse<List<GankItemBean>>> daoTestSubscriber = new TestSubscriber<>();
        daoGirlList
                .subscribe(daoTestSubscriber);
        daoTestSubscriber.assertNoErrors();
    }

    @Test
    public void getRandomGirl() throws Exception {
        repository.getRandomGirl(1);
        verify(api, times(1)).getRandomGirl(1);
    }

}