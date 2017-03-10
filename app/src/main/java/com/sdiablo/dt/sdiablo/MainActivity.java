package com.sdiablo.dt.sdiablo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView mWebview;
    WebSettings mWebSettings;
    SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebview = (WebView) findViewById(R.id.sDiabloWebview);;

        mWebview.clearCache(true);
        mWebview.clearHistory();

        mWebSettings = mWebview.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebSettings.setSaveFormData(false);
        mWebSettings.setSavePassword(false);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);

        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        mWebview.loadUrl("http://120.24.39.174");

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {

            }


            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mSwipeRefresh.setRefreshing(false);
            }
        });

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.sDiabloRefresh);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebview.reload();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            // mWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }


}
