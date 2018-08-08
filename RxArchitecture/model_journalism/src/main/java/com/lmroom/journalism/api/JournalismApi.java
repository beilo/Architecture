package com.lmroom.journalism.api;

import com.lmroom.journalism.bean.JournalismBean;
import com.lmroom.journalism.bean.JournalismResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JournalismApi {
    String HOST = "http://api.apishop.net/";

    @GET("common/news/getNews")
    Flowable<JournalismResponse<JournalismBean>> getJournalism(@Query("apiKey") String apiKey,
                                                               @Query("channel") String channel,
                                                               @Query("num") int num,
                                                               @Query("start") int start);
}
