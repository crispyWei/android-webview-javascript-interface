package com.example.webviewjs;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class MyWebViewClient extends WebViewClient {
    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i("TAG", "shouldOverrideUrlLoading: " + url);
        try{
            if( url.startsWith("http:") || url.startsWith("https:") ) {
                return false;
            }else if( url.startsWith("psvs://") ) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity( intent );
                return true;
            }else{
                return true;
            }
        }catch (Exception e){
            return true;
        }
    }
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        view.loadUrl(url);
        Log.i("TAG", "shouldOverrideUrlLoading: " + url);
        if( url.startsWith("http:") || url.startsWith("https:") ) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity( intent );
        return true;
    }
    @Override
    public void onPageFinished(WebView view, String url) {
        Log.i("TAG", "onPageFinished: " + url);
    }
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        Log.v("TAG", "onLoadResource: " + "onLoadResource");
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.v("TAG", "onPageStarted: " + "onPageStarted");
    }
}
