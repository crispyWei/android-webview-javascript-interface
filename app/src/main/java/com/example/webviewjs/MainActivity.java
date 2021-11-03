package com.example.webviewjs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //========================================================================
        //webview 載入網頁
        //========================================================================
//        mWebView = new WebView(MainActivity.this);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("http://10.0.2.2:8080/webViewJS/");
        WebSettings mWebViewSetting = mWebView.getSettings();
        mWebViewSetting.setJavaScriptEnabled(true);
        mWebViewSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebViewSetting.setCacheMode(mWebViewSetting.LOAD_CACHE_ELSE_NETWORK);
        mWebViewSetting.setDomStorageEnabled(true);
        mWebView.setWebContentsDebuggingEnabled(true);
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    request.grant(request.getResources());
                }
            }
        });
        //Android要對應到Javascript變數名稱
        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        mWebView.setWebViewClient(new MyWebViewClient());
        //setContentView(mWebView);
        //========================================================================
        Button send = (Button) findViewById(R.id.butotn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.evaluateJavascript("javascript:ChangeStatus('native button evaluateJavascript callback', '#961d29')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //Javascript 回傳
                        Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    protected void onDestroy() {
        mWebView.removeJavascriptInterface("Android");
        super.onDestroy();
    }

    class WebAppInterface {
        Context mContext;
        WebAppInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public void showAndroidToast(String toastText) {
            Toast.makeText(mContext, toastText, Toast.LENGTH_SHORT).show();
            mWebView.post(new Runnable() {
                public void run() {
                    mWebView.loadUrl("javascript:ChangeStatus('loadUrl callback Toast', '#961d29')");
                }
            });
        }
    }
}


