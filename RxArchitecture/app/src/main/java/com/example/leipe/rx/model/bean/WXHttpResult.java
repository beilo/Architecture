package com.example.leipe.rx.model.bean;

import java.io.Serializable;

/** 网络基础类
 * Created by 被咯苏州 on 2016/9/6.
 */
public class WXHttpResult<T> implements Serializable {
    private int code;
    private String msg;
    public T newslist;

    public boolean success(){
        return code == 200;
    }

    public WXHttpResult() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getNewslist() {
        return newslist;
    }

    public void setNewslist(T newslist) {
        this.newslist = newslist;
    }
}
