package com.mperfit.perfit.ui.webview;

import android.content.Intent;
import android.widget.FrameLayout;

import com.mperfit.perfit.R;
import com.mperfit.perfit.app.App;
import com.mperfit.perfit.base.SimpleActivity;
import com.mperfit.perfit.utils.SystemUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * Created by zhangbing on 2016/12/15.
 */

public class WebViewActivity extends SimpleActivity {
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    private WebView wvDetailContent;
    private String url;

    @Override
    protected int getLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initEventAndData() {
        url = mContext.getIntent().getStringExtra("url");
        wvDetailContent = new WebView(mContext.getApplication());
        flContainer.addView(wvDetailContent, 0);
        WebSettings webSettings = wvDetailContent.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (SystemUtil.isNetworkConnected()) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }

        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        wvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });

        wvDetailContent.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        destroyWebView();
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

    private void destroyWebView() {
        if (wvDetailContent != null) {
            wvDetailContent.pauseTimers();
            wvDetailContent.removeAllViews();
            wvDetailContent.destroy();
            wvDetailContent = null;
        }
    }



}
