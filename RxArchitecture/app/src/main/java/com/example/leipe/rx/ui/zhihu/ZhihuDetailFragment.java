package com.example.leipe.rx.ui.zhihu;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.leipe.rx.R;
import com.example.leipe.rx.base.BaseFragment;
import com.example.leipe.rx.base.CommonSubscriber;
import com.example.leipe.rx.model.bean.DetailExtraBean;
import com.example.leipe.rx.model.bean.ZhihuDetail;
import com.example.leipe.rx.model.bean.ZhihuDetailBean;
import com.example.leipe.rx.util.HtmlUtil;
import com.example.leipe.rx.viewmodel.ZhihuDetailViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * Created by 被咯苏州 on 2017/7/2.
 */

public class ZhihuDetailFragment extends BaseFragment implements View.OnClickListener {
    private final int detail_layout = R.layout.fragment_zhihu_detail;
    // 上部
    private ImageView img_detail_bar;
    private CollapsingToolbarLayout clp_toolbar;
    private Toolbar toolbar;
    // 中部
    private NestedScrollView recycler_list;
    private WebView view_main;
    // 下部
    private TextView tv_detail_bottom_like;
    private TextView tv_detail_bottom_comment;
    private TextView tv_detail_bottom_share;

    int id = 0;
    int allNum = 0;
    int shortNum = 0;
    int longNum = 0;
    String shareUrl;
    String imgUrl;
    boolean isBottomShow = true;
    boolean isImageShow = false;
    boolean isTransitionEnd = false;
    boolean isNotTransition = false;

    private ZhihuDetailViewModel viewModel;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        img_detail_bar = view.findViewById(R.id.detail_bar_image);
        clp_toolbar = view.findViewById(R.id.clp_toolbar);
        toolbar = view.findViewById(R.id.toolbar);

        recycler_list = view.findViewById(R.id.nsv_scroller);
        view_main = view.findViewById(R.id.view_main);

        tv_detail_bottom_like = view.findViewById(R.id.tv_detail_bottom_like);
        tv_detail_bottom_comment = view.findViewById(R.id.tv_detail_bottom_comment);
        tv_detail_bottom_share = view.findViewById(R.id.tv_detail_bottom_share);
        super.onViewCreated(view, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Intent intent = _mActivity.getIntent();
        id = intent.getExtras().getInt("id");
        isNotTransition = intent.getBooleanExtra("isNotTransition", false);
        WebSettings settings = view_main.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        view_main.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        (_mActivity.getWindow().getSharedElementEnterTransition()).addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                /**
                 * 测试发现部分手机(如红米note2)上加载图片会变形,没有达到centerCrop效果
                 * 查阅资料发现Glide配合SharedElementTransition是有坑的,需要在Transition动画结束后再加载图片
                 * https://github.com/TWiStErRob/glide-support/blob/master/src/glide3/java/com/bumptech/glide/supportapp/github/_847_shared_transition/DetailFragment.java
                 */
                isTransitionEnd = true;
                if (imgUrl != null) {
                    isImageShow = true;
                    Glide.with(_mActivity)
                            .load(imgUrl)
                            .into(img_detail_bar);
                }
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });

        loadDetailData();
    }

    private void loadDetailData() {
        ZhihuDetailViewModel.Factory factory = new ZhihuDetailViewModel.Factory();
        viewModel = ViewModelProviders.of(this,factory).get(ZhihuDetailViewModel.class);
        CommonSubscriber<ZhihuDetailBean> infoFlowable = viewModel.getDetailInfo(id)
                .subscribeWith(new CommonSubscriber<ZhihuDetailBean>(_mActivity) {
                    @Override
                    public void onNext(ZhihuDetailBean zhihuDetailBean) {
                        loadWebViewInfo(zhihuDetailBean);
                    }
                });
        CommonSubscriber<DetailExtraBean> extraInfoFlowable = viewModel.getDetailExtraInfo(id)
                .subscribeWith(new CommonSubscriber<DetailExtraBean>(_mActivity) {
                    @Override
                    public void onNext(DetailExtraBean detailExtraBean) {
                        loadBottomInfo(detailExtraBean);
                    }
                });
        











        Wrong 3rd argument type.
                Found: 'io.reactivex.functions.BiFunction<com.example.leipe.rx.model.bean.ZhihuDetailBean,
                        com.example.leipe.rx.model.bean.DetailExtraBean,
                                java.lang.String>',
                required: 'io.reactivex.functions.BiFunction<? super com.example.leipe.rx.model.bean.ZhihuDetailBean,
                        ? super com.example.leipe.rx.model.bean.ZhihuDetailBean,
        ? extends java.lang.String>' more...





    }

    private void loadWebViewInfo(ZhihuDetailBean zhihuDetailBean) {
        imgUrl = zhihuDetailBean.getImage();
        shareUrl = zhihuDetailBean.getShare_url();
        if (isNotTransition) {
            Glide.with(_mActivity).load(imgUrl).into(img_detail_bar);
        } else {
            if (!isImageShow && isTransitionEnd) {
                Glide.with(_mActivity).load(imgUrl).into(img_detail_bar);
            }
        }
        clp_toolbar.setTitle(zhihuDetailBean.getTitle());
        String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(),zhihuDetailBean.getCss(),zhihuDetailBean.getJs());
        view_main.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    private void loadBottomInfo(DetailExtraBean detailExtraBean) {
        tv_detail_bottom_like.setText(String.format("%d个赞",detailExtraBean.getPopularity()));
        tv_detail_bottom_comment.setText(String.format("%d条评论",detailExtraBean.getComments()));
        allNum = detailExtraBean.getComments();
        shortNum = detailExtraBean.getShort_comments();
        longNum = detailExtraBean.getLong_comments();
    }

    @Override
    protected int getLayoutId() {
        return detail_layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_detail_bottom_like:
                break;
            case R.id.tv_detail_bottom_comment:
                break;
            case R.id.tv_detail_bottom_share:
                break;
        }
    }
}
