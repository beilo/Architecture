package com.minister.architecture.ui.gank;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.minister.architecture.R;
import com.minister.architecture.app.MyApp;
import com.minister.architecture.base.BaseSupportFragment;

/**
 * Gank detail
 * Created by leipe on 2017/9/22.
 */

public class TechDetailFragment extends BaseSupportFragment {
    public static final String URL = "url";

    private View _mView;
    Toolbar toolbar;
    WebView webContainer;

    public static TechDetailFragment newInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        TechDetailFragment fragment = new TechDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        _mView = inflater.inflate(R.layout.fragment_tech_detail, container, false);
        return _mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = _mView.findViewById(R.id.toolbar);
        webContainer = _mView.findViewById(R.id.web_container);
        initView();
    }

    private void initView() {
        setToolbar(toolbar, "Android Detail");
        initWebView();
        webContainer.loadUrl(getArguments().getString(URL));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
    }

    private void initWebView() {
        WebSettings settings = webContainer.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        if (isNetworkConnected(MyApp.getInstance())) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webContainer.setWebViewClient(new WebViewClient() {
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

    public boolean isNetworkConnected(MyApp app) {
        ConnectivityManager connectivityManager = (ConnectivityManager) app.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
