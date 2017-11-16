package com.minister.architecture.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.database.sqlite.SQLiteDatabase;

import com.minister.architecture.BuildConfig;
import com.minister.architecture.model.bean.DaoMaster;
import com.minister.architecture.model.bean.DaoSession;
import com.minister.architecture.model.bean.GankItemBean;
import com.minister.architecture.model.http.result.GankHttpResponse;
import com.minister.architecture.repository.GankRepository;
import com.minister.architecture.ui.gank.child.TechListFragment;
import com.minister.architecture.util.RxHelpTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by leipe on 2017/11/7.
 * @author leipe
 */
@RunWith(RobolectricTestRunner.class) //run test with roboteletric
@Config(constants = BuildConfig.class, sdk = 19)
public class GankViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private GankRepository repository;
    private GankViewModel viewModel;

    private GankHttpResponse<List<GankItemBean>> gankHttpResponse;
    private List<GankItemBean> list;

    @Before
    public void setUp() throws Exception {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(RuntimeEnvironment.application, null, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster dao = new DaoMaster(db);
        DaoSession daoSession = dao.newSession();

        repository = mock(GankRepository.class);
        viewModel = new GankViewModel(repository, daoSession);
        RxHelpTestUtil.asyncToSync();


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
    public void getGirlList() throws Exception {

        // repository.getGirlList(...)，参数为任意int，返回Observable.just(gankHttpResponse)对象。
        when(repository.getGirlList(anyInt(), anyInt()))
                .thenReturn(Flowable.<GankHttpResponse<List<GankItemBean>>>just(gankHttpResponse));

        // 调用viewModel.getGirlList()
        Flowable<List<GankItemBean>> flowable = viewModel.getGirlList();
        TestSubscriber<List<GankItemBean>> testSubscriber = new TestSubscriber<>();
        flowable.subscribe(testSubscriber);

        // 如何效验返回的参数是否正确
        testSubscriber.assertNoErrors();
        Assert.assertEquals(testSubscriber.valueCount(), 1);
        Assert.assertEquals(testSubscriber.values().get(0), list);

        // 验证 repository.getGirlList(10,1)是否被调用，并校验传入参数
        verify(repository, times(1)).getGirlList(10, 1);
    }

    @Test
    public void pullUpLoadGirl() throws Exception {
        when(repository.getGirlList(10, 2)).thenReturn(Flowable.just(gankHttpResponse));

        Flowable<List<GankItemBean>> flowable = viewModel.pullUpLoadGirl();
        TestSubscriber<List<GankItemBean>> testSubscriber = new TestSubscriber<>();
        flowable
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        Assert.assertEquals(testSubscriber.values().size(), 1);
        Assert.assertEquals(testSubscriber.values().get(0), list);
    }

    @Test
    public void getTechList() {
        when(repository.getTechList(anyString(), anyInt(), anyInt())).thenReturn(Flowable.just(gankHttpResponse));

        Flowable<List<GankItemBean>> flowable = viewModel.getTechList();
        TestSubscriber<List<GankItemBean>> testSubscriber = new TestSubscriber<>();
        flowable
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        Assert.assertEquals(testSubscriber.values().size(), 1);
        Assert.assertEquals(testSubscriber.values().get(0), list);
    }

    @Test
    public void pullUpLoadTech() throws Exception {
        // 调用repository.getTechList
        // anyString(), anyInt(), anyInt()  参数如果用any形式那么所有参数都应该用any,不能混用 Invalid use of argument matchers!
        when(repository.getTechList("Android", 10, 2))
                .thenReturn(Flowable.<GankHttpResponse<List<GankItemBean>>>just(gankHttpResponse));

        Flowable<List<GankItemBean>> flowable = viewModel.pullUpLoadTech(new TechListFragment.TechListCallback() {
            @Override
            public void call() {
                Assert.assertEquals("1", "1");
            }
        });

        // https://www.infoq.com/articles/Testing-RxJava2 // 使rx进入error
        Exception exception = new RuntimeException("boom!");
        TestSubscriber<List<GankItemBean>> testSubscriber = new TestSubscriber<>();
        flowable
                // .concatWith(Flowable.<List<GankItemBean>>error(exception))
                .subscribe(testSubscriber);
        // 断言testSubscriber没有error
        testSubscriber.assertNoErrors();
        // 对比数据
        Assert.assertEquals(testSubscriber.values().get(0), list);
        // 判断repository.getTechList是否调用一次
        verify(repository, times(1)).getTechList("Android", 10, 2);
    }

    @Test
    public void getRandomGirl() throws Exception {
        when(repository.getRandomGirl(1)).thenReturn(Flowable.just(gankHttpResponse));

        Flowable<List<GankItemBean>> flowable = viewModel.getRandomGirl();
        TestSubscriber<List<GankItemBean>> testSubscriber = new TestSubscriber<>();
        flowable
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        Assert.assertEquals(testSubscriber.values().size(), 1);
        Assert.assertEquals(testSubscriber.values().get(0), list);
    }
}