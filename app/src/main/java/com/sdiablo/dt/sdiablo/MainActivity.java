package com.sdiablo.dt.sdiablo;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    WebView mWebView;
    WebSettings mWebSettings;
    SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.sDiabloWebview);

        mWebView.clearCache(true);
        mWebView.clearHistory();

        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebSettings.setSaveFormData(false);
        mWebSettings.setSavePassword(false);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);

        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        mWebView.loadUrl("https://120.24.39.174");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            private WebResourceResponse editResponse(String file) {
                try {
                    if (file.equals("require.js")) {
                        return new WebResourceResponse("text/javascript", "utf-8", getAssets().open("require.js"));
                    }

                    else if (file.equals("jquery-1.11.3.min.js")) {
                        return new WebResourceResponse("text/javascript", "utf-8", getAssets().open("metronic/plugins/jquery-1.11.3.min.js"));
                    }
                    else if (file.equals("angular.min.js")) {
                        return new WebResourceResponse("text/javascript", "utf-8", getAssets().open("angular-1.3.9/angular.min.js"));
                    }
                    else if (file.equals("angular-route.min.js")) {
                        return new WebResourceResponse("text/javascript", "utf-8", getAssets().open("angular-1.3.9/angular-route.min.js"));
                    }
                    else if (file.equals("angular-resource.min.js")) {
                        return new WebResourceResponse("text/javascript", "utf-8", getAssets().open("angular-1.3.9/angular-resource.min.js"));
                    }
                    else if (file.equals("bootstrap.min.js")) {
                        return new WebResourceResponse("text/javascript", "utf-8", getAssets().open("bootstrap/js/bootstrap.min.js"));
                    }
                    else if (file.equals("ui-bootstrap-tpls-0.14.3.js")) {
                        return new WebResourceResponse("text/javascript", "utf-8", getAssets().open("bootstrap/ui-bootstrap-tpls-0.14.3.js"));
                    }

                    else if (file.equals("bootstrap.min.css")) {
                        return new WebResourceResponse("text/css", "utf-8", getAssets().open("bootstrap/css/bootstrap.min.css"));
                    }
                    else if (file.equals("bootstrap-customer.css")) {
                        return new WebResourceResponse("text/css", "utf-8", getAssets().open("bootstrap/css/bootstrap-customer.css"));
                    }
                    else if (file.equals("font-awesome.min.css")){
                        return new WebResourceResponse("text/css", "utf-8", getAssets().open("font-awesome/css/font-awesome.min.css"));
                    }
                    else if (file.equals("style-metronic.css")) {
                        return new WebResourceResponse("text/css", "utf-8", getAssets().open("metronic/css/style-metronic.css"));
                    }
                    else if (file.equals("style.css")) {
                        return new WebResourceResponse("text/css", "utf-8", getAssets().open("metronic/css/style.css"));
                    }
                    else if (file.equals("style-responsive.css")) {
                        return new WebResourceResponse("text/css", "utf-8", getAssets().open("metronic/css/style-responsive.css"));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //需处理特殊情况
                return null;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (Build.VERSION.SDK_INT < 21) {
                    if (url.contains("require.js")) {
                        return editResponse("require.js");
                    }
                    else if (url.contains("jquery-1.11.3.min.js")) {
                        return editResponse("jquery-1.11.3.min.js");
                    }
                    else if (url.contains("angular.min.js")) {
                        return editResponse("angular.min.js");
                    }
                    else if (url.contains("angular-route.min.js")) {
                        return editResponse("angular-route.min.js");
                    }
                    else if (url.contains("angular-resource.min.js")) {
                        return editResponse("angular-resource.min.js");
                    }
                    else if (url.contains("bootstrap.min.js")) {
                        return editResponse("bootstrap.min.js");
                    }
                    else if (url.contains("ui-bootstrap-tpls-0.14.3.js")) {
                        return editResponse("ui-bootstrap-tpls-0.14.3.js");
                    }

                    else if (url.contains("bootstrap.min.css")) {
                        return editResponse("bootstrap.min.css");
                    }
                    else if (url.contains("bootstrap-customer.css")) {
                        return editResponse("bootstrap-customer.css");
                    }
                    else if (url.contains("font-awesome.min.css")) {
                        return editResponse("font-awesome.min.css");
                    }
                    else if (url.contains("style-metronic.css")) {
                        return editResponse("style-metronic.css");
                    }
                    else if (url.contains("style.css")) {
                        return editResponse("style.css");
                    }
                    else if (url.contains("style-responsive.css")) {
                        return editResponse("style-responsive");
                    }
                }
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= 21) {
                    String url = request.getUrl().toString();
                    if (!TextUtils.isEmpty(url) && url.contains("jquery-1.11.3.min.js")) {
                        return editResponse("jquery-1.11.3.min.js");
                    }
                    else if (!TextUtils.isEmpty(url) && url.contains("angular.min.js")) {
                        return editResponse("angular.min.js");
                    }
                    else if (!TextUtils.isEmpty(url) && url.contains("angular-route.min.js")) {
                        return editResponse("angular-route.min.js");
                    }
                    else if (!TextUtils.isEmpty(url) && url.contains("angular-resource.min.js")) {
                        return editResponse("angular-resource.min.js");
                    }

                    else if (!TextUtils.isEmpty(url) && url.contains("bootstrap.min.js")) {
                        return editResponse("bootstrap.min.js");
                    }
                    else if (!TextUtils.isEmpty(url) && url.contains("ui-bootstrap-tpls-0.14.3.js")) {
                        return editResponse("ui-bootstrap-tpls-0.14.3.js");
                    }

                    else if (!TextUtils.isEmpty(url) && url.contains("bootstrap.min.css")) {
                        return editResponse("bootstrap.min.css");
                    }

                    else if (!TextUtils.isEmpty(url) && url.contains("bootstrap-customer.css")) {
                        return editResponse("bootstrap-customer.css");
                    }
                    else if (!TextUtils.isEmpty(url) && url.contains("font-awesome.min.css")) {
                        return editResponse("font-awesome.min.css");
                    }
                    else if (!TextUtils.isEmpty(url) && url.contains("style-metronic.css")) {
                        return editResponse("style-metronic.css");
                    }
                    else if (!TextUtils.isEmpty(url) && url.contains("style.css")) {
                        return editResponse("style.css");
                    }
                    else if (!TextUtils.isEmpty(url) && url.contains("style-responsive.css")) {
                        return editResponse("style-responsive");
                    }
                }
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (handler != null) {
                    handler.proceed();
                }
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {

            }


            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });

//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                mSwipeRefresh.setRefreshing(false);
//            }
//        });

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.sDiabloRefresh);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.reload();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            // mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }


}
