package com.minister.architecture.repository;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minister.architecture.model.http.GankApi;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author leipe
 */
@RunWith(JUnit4.class)
public class GankRepositoryTest {

    private GankApi api;
    private GankRepository repository;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        api = mock(GankApi.class);
        repository = new GankRepository(api);

    }


    @Test
    public void getTechList() throws Exception {
        repository.getTechList("",10,1);
        verify(api,times(1)).getTechList("",10,1);
    }

    @Test
    public void getGirlList() throws Exception {
        repository.getGirlList(10,1);
        verify(api,times(0)).getGirlList(10,1);
    }

    @Test
    public void getRandomGirl() throws Exception {
        repository.getRandomGirl(1);
        verify(api,times(1)).getRandomGirl(1);
    }


    @Test
    public void test() throws Exception{
        String images = "[]";
        int currentIndex;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        ImagesBean imagesBean = gson.fromJson(images, ImagesBean.class);
        currentIndex = imagesBean.getCurrentIndex();
    }
}