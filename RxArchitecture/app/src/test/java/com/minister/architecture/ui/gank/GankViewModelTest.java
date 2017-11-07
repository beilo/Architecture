package com.minister.architecture.ui.gank;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.minister.architecture.model.bean.GankItemBean;
import com.minister.architecture.model.http.result.GankHttpResponse;
import com.minister.architecture.repository.GankRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by leipe on 2017/11/7.
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class GankViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private GankRepository repository;
    private GankViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        repository = mock(GankRepository.class);
        viewModel = new GankViewModel(repository);
    }

    @Test
    public void getGirlList() throws Exception {
        List<GankItemBean> list = new ArrayList<>();
        GankHttpResponse gankHttpResponse = new GankHttpResponse(true, list);
        // repository.getGirlList(...)，参数为任意int，返回Observable.just(gankHttpResponse)对象。
        when(repository.getGirlList(anyInt(), anyInt()))
                .thenReturn(Flowable.<GankHttpResponse<List<GankItemBean>>>just(gankHttpResponse));
        // 调用viewModel.getGirlList()
        viewModel.getGirlList();
        // 验证 repository.getGirlList(10,1)是否被调用，并校验传入参数
        verify(repository, times(1)).getGirlList(10, 1);
        // todo 如何效验返回的参数是否正确


    }
}