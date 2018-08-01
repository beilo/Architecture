package com.lmroom.journalism.repository;

import com.lmroom.baselib.http.RetrofitUtil;
import com.lmroom.journalism.api.JokeApi;
import com.lmroom.journalism.api.JournalismApi;
import com.lmroom.journalism.bean.JokeBean;
import com.lmroom.journalism.bean.JokeResponse;
import com.lmroom.journalism.bean.JournalismBean;
import com.lmroom.journalism.bean.JournalismResponse;

import io.reactivex.Flowable;

public class JournalismRepository {
    private static class JournalismHolder{
        private static final JournalismRepository INSTALL = new JournalismRepository();
    }
    private JournalismApi journalismApi;
    private JokeApi jokeApi;
    private JournalismRepository(){
        this.journalismApi = RetrofitUtil.getInstall().getRetrofit(JournalismApi.HOST)
                .create(JournalismApi.class);
        this.jokeApi = RetrofitUtil.getInstall().getRetrofit(JokeApi.HOST)
                .create(JokeApi.class);
    }
    public static final JournalismRepository getInstall(){
        return JournalismHolder.INSTALL;
    }

    public Flowable<JournalismResponse<JournalismBean>> getJournalismList(String channel,
                                                                          int num,
                                                                          int start){
        String apiKey = "GZdx6516fc9e1b0f5871765d6d7c3cc93e1dc3762b946f3";
        return journalismApi.getJournalism(apiKey,
               channel, num, start);
    }

    public Flowable<JokeResponse<JokeBean>> getJokeList(int pagesize,
                                                        int page){
        String apiKey = "fc00c21487c00893b75747a5565c595d";
        String time = String.valueOf((System.currentTimeMillis()/1000));
        String sort = "desc";
        return jokeApi.getJokeBeanList(apiKey,pagesize,page,time,sort);
    }

}
