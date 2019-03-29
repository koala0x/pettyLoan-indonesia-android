package com.github.lzyzsd.jsbridge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 如果要自定义WebViewClient必须要集成此类
 * Created by bruce on 10/28/15.
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;
    private PageLoadFinshListener pageLoadFinshListener;
    private Context mcontext;

    public BridgeWebViewClient(BridgeWebView webView, Context context) {
        this.webView = webView;
        this.mcontext = context;
    }

    public void setPageLoadFinshListener(PageLoadFinshListener pageLoadFinshListener) {
        this.pageLoadFinshListener = pageLoadFinshListener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (url.indexOf("tel:") >= 0) {// 页面上有数字会导致系统会自动连接电话,屏蔽此功能
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mcontext.startActivity(intent);
            return true;
        }
        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            webView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else {
            return this.onCustomShouldOverrideUrlLoading(url) ? true : super.shouldOverrideUrlLoading(view, url);
        }
    }

    // 增加shouldOverrideUrlLoading在api》=24时
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String url = request.getUrl().toString();
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
                webView.handlerReturnData(url);
                return true;
            } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
                webView.flushMessageQueue();
                return true;
            } else {
                return this.onCustomShouldOverrideUrlLoading(url) ? true : super.shouldOverrideUrlLoading(view, request);
            }
        } else {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (pageLoadFinshListener != null) {
            pageLoadFinshListener.pageStart();
        }

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (pageLoadFinshListener != null) {
            pageLoadFinshListener.pageFinish();
        }
        if (BridgeWebView.toLoadJs != null) {
            BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
        }

        //
        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }

        //
        onCustomPageFinishd(view, url);

    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (pageLoadFinshListener != null) {
            pageLoadFinshListener.pageError();
        }
    }

    protected boolean onCustomShouldOverrideUrlLoading(String url) {
        return false;
    }


    protected void onCustomPageFinishd(WebView view, String url) {

    }

    public interface PageLoadFinshListener {
        //页面开始
        void pageStart();

        //页面结束
        void pageFinish();

        //页面错误
        void pageError();
    }


}