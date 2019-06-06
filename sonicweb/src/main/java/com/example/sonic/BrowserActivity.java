
package com.example.sonic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonic.util.SonicJavaScriptInterface;
import com.example.sonic.util.SonicRuntimeImpl;
import com.example.sonic.util.SonicSessionClientImpl;
import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A demo browser activity
 * In this demo there are three modes,
 * sonic mode: sonic mode means webview loads html by sonic,
 * offline mode: offline mode means webview loads html from local offline packages,
 * default mode: default mode means webview loads html in the normal way.
 */

public class BrowserActivity extends AppCompatActivity {

    private String TAG = BrowserActivity.class.getSimpleName();

    public static final int MODE_DEFAULT = 0;//默认使用网络数据
    public static final int MODE_SONIC = 1;//使用本地数据
    public static final int MODE_SONIC_WITH_OFFLINE_CACHE = 2;//自动选择资源

    public final static String PARAM_URL = "param_url";
    public final static String PARAM_MODE = "param_mode";
    String url;

    private SonicSession sonicSession;
    //    ProgressBar progressBar;
    WebView webView;
    private ImageView mCaishenIv;
    private LinearLayout mLoadingLinear;
    private TextView mLoadTv;

    public static void start(Activity activity, String url) {
        startBrowserActivity(activity, url, 0);
    }

    private static void startBrowserActivity(Activity activity, String url, int mode) {
        Intent intent = new Intent(activity, BrowserActivity.class);
        intent.putExtra(BrowserActivity.PARAM_URL, url);
        intent.putExtra(BrowserActivity.PARAM_MODE, mode);
        activity.startActivity(intent);
    }

    public static void startForResult(Activity activity, String url, int requestCode) {
        Intent intent = new Intent(activity, BrowserActivity.class);
        intent.putExtra(BrowserActivity.PARAM_URL, url);
        intent.putExtra(BrowserActivity.PARAM_MODE, 0);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setBackgroundDrawable(null);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.sonicweb_activity_browser);
        initView();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void initView() {
        Intent intent = getIntent();
//        test(intent);
        url = intent.getStringExtra(PARAM_URL);
        Log.d(TAG, url);
        int mode = intent.getIntExtra(PARAM_MODE, -1);

        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }

        SonicSessionClientImpl sonicSessionClient = null;

        // if it's sonic mode , startup sonic session at first time
        if (MODE_DEFAULT != mode) { // sonic mode
            SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
            sessionConfigBuilder.setSupportLocalServer(true);

            // if it's offline pkg mode, we need to intercept the session connection
            if (MODE_SONIC_WITH_OFFLINE_CACHE == mode) {
                sessionConfigBuilder.setCacheInterceptor(new SonicCacheInterceptor(null) {
                    @Override
                    public String getCacheData(SonicSession session) {
                        return null; // offline pkg does not need cache
                    }
                });

                sessionConfigBuilder.setConnectionInterceptor(new SonicSessionConnectionInterceptor() {
                    @Override
                    public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
                        return new OfflinePkgSessionConnection(BrowserActivity.this, session, intent);
                    }
                });
            }

            // create sonic session and run sonic flow
            sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
            if (null != sonicSession) {
                sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
            } else {
                // this only happen when a same sonic session is already running,
                // u can comment following codes to feedback as a default mode.
                // throw new UnknownError("create session fail!");
                Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
            }
        }
        webView = (WebView) findViewById(R.id.webview);
        webView.clearCache(true);

        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
//        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);//滚动条在WebView内侧显示
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条在WebView外侧显示

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (curLoadState!=-1){
                    loading(1);
                }
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //6.0以下执行
                //网络未连接
                noNet();

            }

            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //6.0以上执行
                noNet();
            }

            @TargetApi(21)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }

            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                //TODO 此时加载网页出现问题
//                setVisibility(progressBar,View.INVISIBLE);
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return null;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                progressBar.setProgress(newProgress);
            }
        });

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, this), "bridge");

        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);



        mCaishenIv = (ImageView) findViewById(R.id.iv_caishen);
        mLoadingLinear = (LinearLayout) findViewById(R.id.linear_loading);
        mLoadTv = (TextView) findViewById(R.id.tv_load);

        mLoadingLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curLoadState == -1) {//加载失败
                    loading(0);
                    webView.loadUrl(url);
                }
            }
        });

        // webview is ready now, just tell session client to bind
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            loading(0);
            webView.loadUrl(url);
        }
    }


    @Override
    protected void onDestroy() {
        if (null != sonicSession) {
            sonicSession.destroy();
            webView.destroy();
            sonicSession = null;
        }
        super.onDestroy();
    }


    private static class OfflinePkgSessionConnection extends SonicSessionConnection {

        private final WeakReference<Context> context;

        public OfflinePkgSessionConnection(Context context, SonicSession session, Intent intent) {
            super(session, intent);
            this.context = new WeakReference<Context>(context);
        }

        @Override
        protected int internalConnect() {
            Context ctx = context.get();
            if (null != ctx) {
                try {
                    InputStream offlineHtmlInputStream = ctx.getAssets().open("sonic-demo-index.html");
                    responseStream = new BufferedInputStream(offlineHtmlInputStream);
                    return SonicConstants.ERROR_CODE_SUCCESS;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return SonicConstants.ERROR_CODE_UNKNOWN;
        }

        @Override
        protected BufferedInputStream internalGetResponseStream() {
            return responseStream;
        }

        @Override
        protected String internalGetCustomHeadFieldEtag() {
            return SonicSessionConnection.CUSTOM_HEAD_FILED_ETAG;
        }

        @Override
        public void disconnect() {
            if (null != responseStream) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getResponseCode() {
            return 200;
        }

        @Override
        public Map<String, List<String>> getResponseHeaderFields() {
            return new HashMap<>(0);
        }

        @Override
        public String getResponseHeaderField(String key) {
            return "";
        }
    }

    public void setVisibility(View view, int visble) {
        if (view.getVisibility() != visble) {
            view.setVisibility(visble);
        }
    }

    public void noNet() {
        webView.loadUrl("about:blank");// 避免出现默认的错误界面
        loading(-1);
    }

    /**
     * 加载三种情况：-1.加载失败 0.记载中 1.加载完成
     */
    int curLoadState = 0;
    public void loading(int flag) {
        this.curLoadState = flag;
        switch (flag) {
            case -1:
//                Glide.with(this)
//                        .load(R.drawable.icon_category_normal)
//                        .into(mCaishenIv);
                mLoadTv.setText("请点击屏幕，重新加载");
//                setVisibility(mLoadingLinear,View.VISIBLE);
                break;
            case 0:
//                Glide.with(this)
//                        .load(R.drawable.icon_category_checked)
////                        .asGif()
////                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .into(mCaishenIv);
                mLoadTv.setText("努力加载中...");
//                setVisibility(mLoadingLinear,View.VISIBLE);
                break;
            case 1:
                setVisibility(mLoadingLinear,View.GONE);
                break;
        }


    }

}
