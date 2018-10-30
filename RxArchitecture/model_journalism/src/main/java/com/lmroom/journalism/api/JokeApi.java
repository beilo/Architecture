package com.lmroom.journalism.api;


import com.lmroom.journalism.bean.JokeBean;
import com.lmroom.journalism.bean.JokeResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JokeApi {
    String HOST="http://v.juhe.cn/";

    @GET("joke/content/list.php")
    Flowable<JokeResponse<JokeBean>> getJokeBeanList(@Query("key") String key,
                                                     @Query("pagesize") int pagesize,
                                                     @Query("page") int page,
                                                     @Query("time") String time,
                                                     @Query("sort") String sort
    );
}
