package com.wag.gamebox.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.wag.gamebox.R;
import com.wag.gamebox.ui.view.TitleBarManger;

import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.layout_top)
    RelativeLayout layoutTop;
    @BindView(R.id.wv_info)
    WebView wvInfo;

    public static final String  URL="url";
    public static final String  TITLE="title";

    public static void actionStart(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }


    @Override
    protected void initTitle() {
        TitleBarManger manger=TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle(getIntent().getStringExtra(TITLE));
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initViews() {

        Log.e(getClass().getSimpleName(),getIntent().getStringExtra(URL));
        //设置可自由缩放网页
        wvInfo.loadUrl(getIntent().getStringExtra(URL));
        //设置自适应屏幕，两者合用
        WebSettings webSettings = wvInfo.getSettings();
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setJavaScriptEnabled(true);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wvInfo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initDatas() {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {finish();
    }
}
