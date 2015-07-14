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
        // ����֧��javascript�ű�
        webView.getSettings().setJavaScriptEnabled(true);
        // ��������ļ�
        webView.getSettings().setAllowFileAccess(true);
        // ������ʾ���Ű�ť
        webView.getSettings().setBuiltInZoomControls(true);
        // ֧������
        webView.getSettings().setSupportZoom(true);
        // ����Cannot call method 'getItem' of null at XXXX/build.js:6�ȴ���
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        // ���õ���λ
        webView.getSettings().setGeolocationEnabled(true);
        // ���ö�λ�����ݿ�·��
        webView.getSettings().setGeolocationDatabasePath(dir);
        // �����Ƿ�����ͨ��file url���ص�Javascript���Է���������Դ�������������ļ���http,https��������Դ��
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        /**
         * ��WebView��ʾͼƬ����ʹ��������� ������ҳ�������ͣ� 1��LayoutAlgorithm.NARROW_COLUMNS ��
         * ��Ӧ���ݴ�С 2��LayoutAlgorithm.SINGLE_COLUMN:��Ӧ��Ļ�����ݽ��Զ�����
         */
        // webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        // //�����ı�����
        // webView.getSettings().setDefaultTextEncodingName("utf-8");
        // webView.getSettings().setAppCacheEnabled(true);

        //���û���ģʽ
        if (Network.isNetwork(this)) {

            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        // �������������� ��������ã����ڵ����ҳ�ı������ʱ�����ܵ�������̼�����Ӧ������һЩ�¼���
        webView.requestFocus();
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.addJavascriptInterface(new WebAppInterface(this),
                "myInterfaceName");
//        webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");

    }


    private class MyWebViewClient extends WebViewClient {
        // ����������ʱ��ʹ�õ�ǰ�� WebView������ʹ��ϵͳ���������
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
        // ������ҳ���صĽ�����
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        // �õ���ǰ��ҳ����
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

        //��js�е���window.AndroidWebView.showInfoFromJs(name)����ᴥ���˷�����
        @JavascriptInterface
        public void showInfoFromJs(String name) {
            Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * �Զ����Android�����JavaScript����֮���������
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
        // ���target ���ڵ���API 17������Ҫ��������ע��
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
