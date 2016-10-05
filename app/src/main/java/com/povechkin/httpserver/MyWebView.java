package com.povechkin.httpserver;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by povechkin on 05.10.2016.
 * Custis production
 */
public class MyWebView extends WebView {
    IOnPostRequest onPostRequest;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnPostRequest(IOnPostRequest onPostRequest) {
        this.onPostRequest = onPostRequest;
    }

    @Override
    public void  postUrl  (String  url, byte[] postData)
    {
        System.out.println("+++++++WebView postUrl:" +url);
        onPostRequest.onPost(url);
        super.postUrl(url, postData);
    }

    interface IOnPostRequest {
        void onPost(String url);
    }
}
