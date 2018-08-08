package com.lmroom.zhihu.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.lmroom.baselib.base.BaseApplication;
import com.lmroom.baselib.base.BaseSupportFragment;
import com.lmroom.baselib.util.HtmlUtil;
import com.lmroom.zhihu.R;
import com.lmroom.zhihu.bean.ZhihuDetailBean;
import com.lmroom.zhihu.util.ZhiHuRxHelp;
import com.lmroom.zhihu.viewmodel.ZhiHuViewModel;

import io.reactivex.functions.Consumer;

/**
 * 日报详情
 * Created by leipe on 2017/9/19.
 */
@Route(path = "/zhihu/detail")
public class ZhiHuDetailFragment extends BaseSupportFragment {
    ZhiHuViewModel mZhihuViewModel;

    private View _mView;
    ImageView mImgContainer;
    WebView mWebContainer;
    Toolbar toolbar;

    @Autowired
    int id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _mView = inflater.inflate(R.layout.zhihu_fragment_daily_detail, container, false);
        ARouter.getInstance().inject(this);
        mZhihuViewModel = ViewModelProviders.of(this).get(ZhiHuViewModel.class);
        return _mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImgContainer = _mView.findViewById(R.id.img_container);
        mWebContainer = _mView.findViewById(R.id.web_container);
        toolbar = _mView.findViewById(R.id.toolbar);

        setToolbar(toolbar,"知乎日报详情");
        initToolbar();
        initWebView();
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
    }

    private void initWebView() {
        WebSettings settings = mWebContainer.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        if (isNetworkConnected()) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        mWebContainer.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri url = request.getUrl();
                String path = url.getPath();
                view.loadUrl(path);
                return true;
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mDisposable
                .add(mZhihuViewModel
                        .getDetailInfo(id)
                        .compose(ZhiHuRxHelp.<ZhihuDetailBean>rxScheduler())
                        .subscribe(new Consumer<ZhihuDetailBean>() {
                            @Override
                            public void accept(@NonNull ZhihuDetailBean zhihuDetailBean) throws Exception {
                                Glide
                                        .with(_mActivity)
                                        .load(zhihuDetailBean.getImage())
                                        .into(mImgContainer);
                                String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(), zhihuDetailBean.getCss(), zhihuDetailBean.getJs());
                                mWebContainer.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }));

    }

    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    @Override
    protected int setTitleBar() {
        return R.id.toolbar;
    }
}
