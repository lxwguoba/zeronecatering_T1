package com.itxwl.html5Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.itxwl.R;

/**
 * Created by on 2017/12/26 0026 10 58.
 * Author  LiuXingWen
 */

public class Html5Activity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_html);
            initView();
        }
    private void initView() {
       webView = (WebView) findViewById(R.id.html5_view);
        String url="file:///android_asset/html/userlogin.html";
        webView.loadUrl("file:///android_asset/userlogin.html");
    }
}
