package com.itlxw.html;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Htmlctivity extends AppCompatActivity {
      private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htmlctivity);
        initView();
    }
    private void initView() {

      /*  //加载html
        StringBuilder sb = new StringBuilder();
        //拼接一段HTML代码
        sb.append("<html.html>");
        sb.append("<head>");
        sb.append("<title>欢迎你</title>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<h2>欢迎你访问<a href=\"http://www.crazyit.org\">"
                + "疯狂Java联盟</a></h2>");
        sb.append("</body>");
        sb.append("</html.html>");*/

      //     webView.loadDataWithBaseURL(null, sb.toString(), "text/html.html", "utf-8", null);
        //使用简单的loadData方法会导致乱码，可能是Android API的Bug
        //webview.loadData(sb.toString(), "text/html.html", "utf-8");
        webView = (WebView) findViewById(R.id.html5_view);
        //加载assets目录下的html
        //加上下面这段代码可以使网页中的链接不以浏览器的方式打开
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
        //得到webview设置
        WebSettings webSettings = webView.getSettings();
        //允许使用javascript
        webSettings.setJavaScriptEnabled(true);
        //设置字符编码
        webSettings.setDefaultTextEncodingName("UTF-8");
        //支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //将WebAppInterface与javascript绑定
        //webview.addJavascriptInterface(new PaymentJavaScriptInterface(),"Android");
        //android assets目录下html文件路径url为 file:///android_asset/profile.html.html
        String url = "file:///android_asset/" + "html.html";
        webView.loadUrl(url);
    }
}
