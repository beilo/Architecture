package com.minister.architecture.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.minister.architecture.model.bean.JokeBean;
import com.minister.architecture.model.bean.JournalismBean;
import com.minister.architecture.repository.JournalismRepository;
import com.minister.architecture.util.RxHelp;

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
                .compose(RxHelp.<JournalismBean>handleResultForJournalism());
    }

    public Flowable<JokeBean> getJokeList(int pagesize,
                                          int page){
        return mJournalismRepository.getJokeList(pagesize,page)
                .compose(RxHelp.<JokeBean>handleResultForJoke());
    }
}
