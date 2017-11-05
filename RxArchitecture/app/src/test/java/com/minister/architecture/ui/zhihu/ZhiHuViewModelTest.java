package com.minister.architecture.ui.zhihu;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.minister.architecture.model.http.ZhihuApis;
import com.minister.architecture.repository.ZhihuRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;

/**
 * Created by leipe on 2017/9/25.
 */
public class ZhiHuViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private ZhihuApis apis;
    private ZhihuRepository repository;
    private ZhiHuViewModel viewModel;

    @Before
    public void setup() {
        apis = spy(ZhihuApis.class);
        repository = new ZhihuRepository(apis);
        viewModel = new ZhiHuViewModel(repository);
    }

    @Test
    public void getHotList() throws Exception {
        assertThat(viewModel.getDailyList(), notNullValue());
    }

    @Test
    public void getDetailInfo() throws Exception {

    }

    @Test
    public void getDailyList() throws Exception {

    }

    @Test
    public void getWelcomeInfo() throws Exception {

    }

}