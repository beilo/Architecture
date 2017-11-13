package com.minister.architecture.ui.gank;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.minister.architecture.R;
import com.minister.architecture.app.MyApp;
import com.minister.architecture.base.BaseSupportFragment;
import com.minister.architecture.sonic.SonicRuntimeImpl;
import com.minister.architecture.sonic.SonicSessionClientImpl;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Gank detail
 * Created by leipe on 2017/9/22.
 */

public class TechDetailFragment extends BaseSupportFragment {
    public static final String URL = "url";

    @Inject
    MyApp app;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.web_container)
    WebView webContainer;

    SonicSession sonicSession;
    SonicSessionClientImpl sonicSessionClient = null;

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
        initSonic();
        View inflate = inflater.inflate(R.layout.fragment_tech_detail, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        initView();
        return inflate;
    }

    @Override
    public void onDestroyView() {
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
        super.onDestroyView();
    }

    private void initSonic() {
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(app), new SonicConfig.Builder().build());
        }
        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sonicSession = SonicEngine.getInstance().createSession(getArguments().getString(URL), sessionConfigBuilder.build());
        if (null != sonicSession) {
            sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
        } else {
            Toast.makeText(_mActivity, "create sonic session fail!", Toast.LENGTH_LONG).show();
        }
        sessionConfigBuilder.setSupportLocalServer(true);
        sessionConfigBuilder.build();
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
        WebView.setWebContentsDebuggingEnabled(true);
        WebSettings settings = webContainer.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
//        if (isNetworkConnected(MyApp.getInstance())) {
//            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        } else {
//            settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
//        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        webContainer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }
            @TargetApi(21)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return null;
            }
        });

        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webContainer);
            sonicSessionClient.clientReady();
        } else { // default mode
            webContainer.loadUrl(getArguments().getString(URL));
        }
    }

    public boolean isNetworkConnected(MyApp app) {
        ConnectivityManager connectivityManager = (ConnectivityManager) app.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
