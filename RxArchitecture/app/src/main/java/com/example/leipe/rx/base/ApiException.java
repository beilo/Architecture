package com.example.leipe.rx.base;

/**
 * Created by codeest on 2016/8/4.
 */
public class ApiException extends Exception {
    int code;

    public ApiException(String msg)
    {
        super(msg);
        this.code = 0;
    }

    public ApiException(int code,String msg){
        super(msg);
        this.code = code;
    }
}
