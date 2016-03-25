package com.firstcode.activity.network;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.firstcode.activity.base.BaseActivity;
import com.firstcode.test.R;

/**
 * Created by wangjinliang on 2016/3/18.
 */
public class WebViewShowActivity extends BaseActivity {
    private static String TAG = "WebViewShowActivity";
    private ProgressBar pb = null;
    private WebView mWebView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_webview_layout);
        initViews();
        initDatas();
    }

    private void initDatas() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100)
                    pb.setVisibility(View.INVISIBLE);
                else {
                    if(pb.getVisibility() == View.INVISIBLE)
                        pb.setVisibility(View.VISIBLE);
                    pb.setProgress(newProgress);
                }
            }
        });
        mWebView.setWebViewClient(new EgWebViewClient());
        mWebView.loadUrl("http://www.baidu.com");
    }

    private void initViews() {
        pb = (ProgressBar)findViewById(R.id.eg_game_webview_pb);
        mWebView = (WebView)findViewById(R.id.eg_game_webview_wv);
    }

    public static void startActivity(Activity mInstance) {
        Intent intent = new Intent(mInstance, WebViewShowActivity.class);
        mInstance.startActivity(intent);
    }

    class EgWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}
