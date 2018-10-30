package com.minister.architecture.model.http;

import com.minister.architecture.model.bean.JournalismBean;
import com.minister.architecture.model.http.result.JournalismResponse;

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
