package com.aimer.shd.webviewjs;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("javascript:vv()");
            }
        });
        initWebView();
        webView.loadUrl("http://192.168.1.118:3000/index.html");
    }

    private void initWebView() {
        String dir = this.getDir("database", Context.MODE_PRIVATE).getPath();
        // 设置支持javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        // 允许访问文件
        webView.getSettings().setAllowFileAccess(true);
        // 设置显示缩放按钮
        webView.getSettings().setBuiltInZoomControls(true);
        // 支持缩放
        webView.getSettings().setSupportZoom(true);
        // 避免Cannot call method 'getItem' of null at XXXX/build.js:6等错误
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        // 启用地理定位
        webView.getSettings().setGeolocationEnabled(true);
        // 设置定位的数据库路径
        webView.getSettings().setGeolocationDatabasePath(dir);
        // 设置是否允许通过file url加载的Javascript可以访问其他的源，包括其他的文件和http,https等其他的源。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        // webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        // //设置文本编码
        // webView.getSettings().setDefaultTextEncodingName("utf-8");
        // webView.getSettings().setAppCacheEnabled(true);

        //设置缓存模式
        if (Network.isNetwork(this)) {

            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        // 触摸焦点起作用 如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        webView.requestFocus();
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.addJavascriptInterface(new WebAppInterface(this),
                "myInterfaceName");
//        webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");

    }


    private class MyWebViewClient extends WebViewClient {
        // 当打开新链接时，使用当前的 WebView，不会使用系统其他浏览器
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("---")) {
                return true;
            }else {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:window.handler.show(document.body.innerHTML);");
            super.onPageFinished(view, url);
        }

    }

    private class MyWebChromeClient extends WebChromeClient {
        // 设置网页加载的进度条
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        // 得到当前网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            if (!pagerUrl.startsWith("file")) {
//                if (titles.equals(schoolName)) {
//                }
//            }
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }


    private class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs(String name) {
            Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 自定义的Android代码和JavaScript代码之间的桥梁类
     *
     * @author 1
     */
    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        // 如果target 大于等于API 17，则需要加上如下注解
        @JavascriptInterface
        public void showToast(String toast) {
            // Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
        }

        public void getImageUrl(String path) {
            Toast.makeText(mContext, path, Toast.LENGTH_SHORT).show();
        }
    }
}
