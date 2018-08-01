package com.lmroom.journalism.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.lmroom.journalism.bean.JokeBean;
import com.lmroom.journalism.bean.JournalismBean;
import com.lmroom.journalism.repository.JournalismRepository;
import com.lmroom.journalism.util.JournalismRxHelp;

import io.reactivex.Flowable;


public class JournalismViewModel extends ViewModel {

    private final JournalismRepository mJournalismRepository;

    public JournalismViewModel() {
        mJournalismRepository = JournalismRepository.getInstall();
    }

    public Flowable<JournalismBean> getJournalismList(String channel,
                                                      int num,
                                                      int start){
        return mJournalismRepository.getJournalismList(channel, num, start)
                .compose(JournalismRxHelp.<JournalismBean>handleResultForJournalism());
    }

    public Flowable<JokeBean> getJokeList(int pagesize,
                                          int page){
        return mJournalismRepository.getJokeList(pagesize,page)
                .compose(JournalismRxHelp.<JokeBean>handleResultForJoke());
    }
}
