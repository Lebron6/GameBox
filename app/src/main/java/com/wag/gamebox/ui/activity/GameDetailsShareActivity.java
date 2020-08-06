package com.wag.gamebox.ui.activity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.entity.ShareInfo;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.Utils;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.wag.gamebox.api.Constant.QQ_APP_ID;

public class GameDetailsShareActivity extends Activity implements OnClickListener {


    private LinearLayout ll_pyq, ll_wxhy, ll_qqhy, ll_qqkj, ll_fzlj;
    public static final String SHARE_URL = "url";
    public static final String SHARE_TITLE = "title";
    public static final String SHARE_CONTENT = "content";
    public static final String SHARE_IMG = "img";
    private ShareInfo shareInfo;
    private Tencent mTencent;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details_share);
        initView();
    }

    public static void actionStart(Context context, String url, String title, String content, String img) {
        Intent intent = new Intent(context, GameDetailsShareActivity.class);
        intent.putExtra(SHARE_URL, url);
        intent.putExtra(SHARE_TITLE, title);
        intent.putExtra(SHARE_CONTENT, content);
        intent.putExtra(SHARE_IMG, img);
        context.startActivity(intent);
    }

    private void initView() {
        ll_pyq = (LinearLayout) findViewById(R.id.pengyouquan);
        ll_pyq.setOnClickListener(this);
        ll_wxhy = (LinearLayout) findViewById(R.id.weixinhaoyou);
        ll_wxhy.setOnClickListener(this);
        ll_qqhy = (LinearLayout) findViewById(R.id.qqhaoyou);
        ll_qqhy.setOnClickListener(this);
        ll_qqkj = (LinearLayout) findViewById(R.id.qqkongjian);
        ll_qqkj.setOnClickListener(this);
        ll_fzlj = (LinearLayout) findViewById(R.id.fuzhilianjie);
        ll_fzlj.setOnClickListener(this);
        ShareInfo.DataBean dataBean = new ShareInfo.DataBean();
        dataBean.setAppLogo(getIntent().getStringExtra(SHARE_IMG));
        dataBean.setContent(getIntent().getStringExtra(SHARE_CONTENT));
        dataBean.setTitle(getIntent().getStringExtra(SHARE_TITLE));
        dataBean.setUrl(getIntent().getStringExtra(SHARE_URL));
        Log.e("分享图片链接",getIntent().getStringExtra(SHARE_IMG));
        shareInfo = new ShareInfo();
        shareInfo.setData(dataBean);
        mTencent = Tencent.createInstance(QQ_APP_ID, this.getApplicationContext());
        bitmap = returnBitMap(shareInfo.getData().getAppLogo());//一进来就缓存
    }

    public  Bitmap returnBitMap(final String url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTencent.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pengyouquan:    //分享至朋友圈
                shareWX(2);
                break;
            case R.id.weixinhaoyou:     //分享至微信好友
                shareWX(1);
                break;
            case R.id.qqhaoyou:         //分享至qq好友
                shareQQ(1);
                break;
            case R.id.qqkongjian:       //分享至qq空间
                shareQQ(2);
                break;
            case R.id.fuzhilianjie:     //复制链接
                if (shareInfo.getData().getUrl() != null) {
                    ClipboardManager cmb = (ClipboardManager) x.app().getSystemService(Context.CLIPBOARD_SERVICE);
                    cmb.setText(shareInfo.getData().getUrl());
                    ToastUtil.showToast("复制完成");
                } else {
                    ToastUtil.showToast("复制失败");
                }
                break;
        }
    }

    private void shareWX(int type) {
        if (shareInfo != null && shareInfo.getData() != null) {
            IWXAPI mWXApi = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, true);
            mWXApi.registerApp(Constant.WX_APP_ID);
if (mWXApi.isWXAppInstalled()){
    WXWebpageObject webpage = new WXWebpageObject();
    webpage.webpageUrl = BaseUrl.getInstence().ipAddress + shareInfo.getData().getUrl();

    WXMediaMessage msg = new WXMediaMessage(webpage);
    msg.title = shareInfo.getData().getTitle();
    msg.description = shareInfo.getData().getContent();
// 图片
    msg.setThumbImage( bitmap);

    SendMessageToWX.Req req = new SendMessageToWX.Req();
//WXSceneTimeline朋友圈    WXSceneSession聊天界面
    req.scene = type == 1 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;//聊天界面
    req.message = msg;
    req.transaction = String.valueOf(System.currentTimeMillis());
    mWXApi.sendReq(req);
}else{
    ToastUtil.showToast("未安装微信客户端");
}

        } else {
            ToastUtil.showToast("获取分享参数失败");
        }
    }


    private void shareQQ(int type) {
        if (shareInfo != null && shareInfo.getData() != null) {
            if (type == 1) {
                Bundle bundle = new Bundle();
                bundle.putString(QQShare.SHARE_TO_QQ_TITLE, shareInfo.getData().getTitle());
                bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, BaseUrl.getInstence().ipAddress + shareInfo.getData().getUrl());
                bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareInfo.getData().getContent());
                bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, BaseUrl.getInstence().ipAddress + shareInfo.getData().getAppLogo());
                mTencent.shareToQQ(this, bundle, new BaseUiListener());
            } else {
                Bundle bundle = new Bundle();
                ArrayList<String> strings = new ArrayList<>();
                strings.add(BaseUrl.getInstence().ipAddress + shareInfo.getData().getAppLogo());
                bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareInfo.getData().getTitle());
                bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareInfo.getData().getUrl());
                bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareInfo.getData().getContent());
                bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, strings);
                mTencent.shareToQzone(this, bundle, new BaseUiListener());
            }
        } else {
            ToastUtil.showToast("获取分享信息失败");
        }
    }

    private class BaseUiListener implements IUiListener {

        protected void doComplete(Object values) {

        }

        @Override
        public void onComplete(Object o) {
            //        mBaseMessageText.setText("onComplete:");
//
//        mMessageText.setText(response.toString());

            doComplete(o);
        }

        @Override

        public void onError(UiError e) {

            Log.e("onError:", "code:" + e.errorCode + ", msg:"

                    + e.errorMessage + ", detail:" + e.errorDetail);

        }

        @Override

        public void onCancel() {

            Log.e("onCancel", "");

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}