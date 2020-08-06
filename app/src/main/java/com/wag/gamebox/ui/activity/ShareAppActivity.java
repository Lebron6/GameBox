package com.wag.gamebox.ui.activity;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.GetPhoneCodeResult;
import com.wag.gamebox.entity.ShareInfo;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.Utils;
import com.wag.gamebox.ui.view.TitleBarManger;
import com.tencent.connect.common.Constants;
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

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Util;

import static com.wag.gamebox.api.Constant.QQ_APP_ID;

/**
 * Created by James on 2018/10/10.
 */
public class ShareAppActivity extends BaseActivity {

    @BindView(R.id.layout_top)
    RelativeLayout layoutTop;
    @BindView(R.id.layout_txt)
    LinearLayout layoutTxt;
    @BindView(R.id.img_qr_code)
    ImageView imgQrCode;
    @BindView(R.id.txt_yellow)
    TextView txtYellow;
    @BindView(R.id.layout_wechat)
    LinearLayout layoutWechat;
    @BindView(R.id.layout_wechat_friends)
    LinearLayout layoutWechatFriends;
    @BindView(R.id.layout_qq)
    LinearLayout layoutQq;
    @BindView(R.id.layout_qqzone)
    LinearLayout layoutQqzone;
    @BindView(R.id.layout_copy)
    LinearLayout layoutCopy;
    private Tencent mTencent;
    private ShareInfo shareInfo;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ShareAppActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("分享APP");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_share_app;
    }

    @Override
    protected void initViews() {
        mTencent = Tencent.createInstance(QQ_APP_ID, this.getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTencent.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initDatas() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getShareAppUrl());
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils(mHandler);
        httpRequestUtils.doGet(params);
        imgQrCode.setOnLongClickListener(onLongClickListener);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取app分享信息", msg.obj.toString());
                    shareInfo = new Gson().fromJson(msg.obj.toString(), ShareInfo.class);
                    if (shareInfo != null && shareInfo.getData() != null) {
                        x.image().bind(imgQrCode, BaseUrl.getInstence().ipAddress + shareInfo.getData().getQrcode());
                    } else {
                        ToastUtil.showToast("获取App信息失败");
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast(msg.obj.toString());
                    break;
            }
        }
    };

    @OnClick({R.id.layout_wechat, R.id.layout_wechat_friends, R.id.layout_qq, R.id.layout_qqzone, R.id.layout_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_wechat:
                shareWX(1);
                break;
            case R.id.layout_wechat_friends:
                shareWX(2);
                break;
            case R.id.layout_qq:
                shareQQ(1);
                break;
            case R.id.layout_qqzone:
                shareQQ(2);
                break;
            case R.id.layout_copy:
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
if(mWXApi.isWXAppInstalled()){
    WXWebpageObject webpage = new WXWebpageObject();
    webpage.webpageUrl = BaseUrl.getInstence().ipAddress + shareInfo.getData().getUrl();

    WXMediaMessage msg = new WXMediaMessage(webpage);
    msg.title = shareInfo.getData().getTitle();
    msg.description = shareInfo.getData().getContent();
    //这块需要注意，图片的像素千万不要太大，不然的话会调不起来微信分享，
    //我在做的时候和我们这的UIMM说随便给我一张图，她给了我一张1024*1024的图片
    //当时也不知道什么原因，后来在我的机智之下换了一张像素小一点的图片好了！
    Bitmap thumb = BitmapFactory.decodeResource(ShareAppActivity.this.getResources(), R.mipmap.x_logo);
    msg.setThumbImage(thumb);
//            msg.thumbData = getWXThumb(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).toByteArray();

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

    public static final int IMAGE_LENGTH_LIMIT = 6291456;


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


    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ShareAppActivity.this);
            builder.setItems(new String[]{"保存二维码"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    imgQrCode.setDrawingCacheEnabled(true);
                    Bitmap imageBitmap = imgQrCode.getDrawingCache();
                    if (imageBitmap != null) {
                        new SaveImageTask().execute(imageBitmap);
                    }
                }
            });
            builder.show();
            return false;
        }
    };

    /**
     * 保存二维码
     */
    private class SaveImageTask extends AsyncTask<Bitmap, Void, String> {
        @Override
        protected String doInBackground(Bitmap... params) {
            String result = "保存失败";
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();
                File file = new File(sdcard + "/" + x.app().getResources().getString(R.string.app_name) + "二维码存储");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File imageFile = new File(file.getAbsolutePath(), new Date().getTime() + ".jpg");
                FileOutputStream outStream = null;
                outStream = new FileOutputStream(imageFile);
                Bitmap image = params[0];
                image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                result = "已成功保存二维码至"+x.app().getResources().getString(R.string.app_name) + "二维码存储文件夹";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            imgQrCode.setDrawingCacheEnabled(false);
        }
    }

}
