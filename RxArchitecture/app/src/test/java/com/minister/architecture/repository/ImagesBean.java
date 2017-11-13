package com.minister.architecture.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeiP on 2016/4/11.
 * 测试Images类
 */
public class ImagesBean {
    public int currentIndex = 0;
    public List<String> urls = new ArrayList<>();

    public ImagesBean(int currentIndex, List<String> urls) {
        this.currentIndex = currentIndex;
        this.urls = urls;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
