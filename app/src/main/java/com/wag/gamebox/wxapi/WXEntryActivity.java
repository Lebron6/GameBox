/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.wag.gamebox.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.app.WagGameBoxApp;
import com.wag.gamebox.entity.WXAccessTokenEntity;
import com.wag.gamebox.entity.ThirdLoginResult;
import com.wag.gamebox.entity.WXUserInfo;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.activity.MainActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static com.wag.gamebox.api.Constant.REQUEST_ACCESS_TOKEN;
import static com.wag.gamebox.api.Constant.REQUEST_FAIL;
import static com.wag.gamebox.api.Constant.REQUEST_SUCCESS;
import static com.wag.gamebox.api.Constant.REQUEST_WX_USER_INFO;

/**
 * 微信客户端回调activity示例
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";
    private static final int RETURN_MSG_TYPE_LOGIN = 1; //登录
    private static final int RETURN_MSG_TYPE_SHARE = 2; //分享


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WagGameBoxApp.api.handleIntent(getIntent(), this);
    }

    /**
     * 处理微信发出的向第三方应用请求app message
     * <p>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    public void onGetMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null) {
            Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
            startActivity(iLaunchMyself);
        }
    }

    /**
     * 处理微信向第三方应用发起的消息
     * <p>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    public void onShowMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null && msg.mediaObject != null
                && (msg.mediaObject instanceof WXAppExtendObject)) {
            WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
Log.e("onReq","onRea");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.i(TAG, "onResp:------>");
        Log.i(TAG, "error_code:---->" + baseResp.errCode);
        int type = baseResp.getType(); //类型：分享还是登录
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝授权
                ToastUtil.showToast("拒绝授权微信登录");
                finish();
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                String message = "";
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    message = "取消了微信登录";
                    finish();
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    message = "取消了微信分享";
                    finish();
                }
                ToastUtil.showToast(message);
                break;
            case BaseResp.ErrCode.ERR_OK:
                //用户同意
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    //用户换取access_token的code，仅在ErrCode为0时有效
                    String code = ((SendAuth.Resp) baseResp).code;
                    Log.i(TAG, "code:------>" + code);
                    //这里拿到了这个code，去做2次请求获取access_token和用户个人信息
                    getAccessToken(code, REQUEST_ACCESS_TOKEN);
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    ToastUtil.showToast("微信分享成功");
                    finish();
                }
                break;
        }
    }



    private void getAccessToken(String code, final int requestType) {
        final RequestParams params = new RequestParams(BaseUrl.getInstence().getWXAccessTokenUrl());
        params.addParameter("appid", Constant.WX_APP_ID);
        params.addParameter("secret", Constant.WX_SECRET);
        params.addParameter("code", code);
        params.addParameter("grant_type", "authorization_code");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onCancelled(CancelledException arg0) {
                Log.e(params.getUri(), "onCancelled");
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Log.e(params.getUri(), "onFailure info =" + arg0.toString());
                noticeResult(requestType, "请求失败");
            }

            @Override
            public void onFinished() {
                Log.e(params.getUri(), "onFinished");
            }

            @Override
            public void onSuccess(String json) {
                noticeResult(requestType, json);
            }
        });

    }

    //获取微信个人信息
    private void getWXUserInfo(String accessToken,String openId, final int requestType) {
        final RequestParams params = new RequestParams(BaseUrl.getInstence().getWXUserInfoUrl());
        params.addParameter("access_token",accessToken);
        params.addParameter("openid",openId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onCancelled(CancelledException arg0) {
                Log.e(params.getUri(), "onCancelled");
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Log.e(params.getUri(), "onFailure info =" + arg0.toString());
                noticeResult(requestType, "请求失败");
            }

            @Override
            public void onFinished() {
                Log.e(params.getUri(), "onFinished");
            }

            @Override
            public void onSuccess(String json) {
                noticeResult(requestType, json);
            }
        });

    }

    private void noticeResult(int type, Object obj) {
        Message msg = new Message();
        msg.what = type;
        msg.obj = obj;
        if (null != mHandler) {
            mHandler.sendMessage(msg);
        }
    }

    //从微信Server获取到openid和accessToken
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REQUEST_ACCESS_TOKEN:
                    Log.e("获取AccessToken参数结果", msg.obj.toString());
                    WXAccessTokenEntity WXAccessTokenEntity = new Gson().fromJson(msg.obj.toString(), WXAccessTokenEntity.class);
                    if (TextUtils.isEmpty(WXAccessTokenEntity.getOpenid())) {
                        ToastUtil.showToast(WXAccessTokenEntity.getErrmsg() + ":" + WXAccessTokenEntity.getErrcode());
                        finish();
                    } else {
                        getWXUserInfo(WXAccessTokenEntity.getAccess_token(), WXAccessTokenEntity.getOpenid(),REQUEST_WX_USER_INFO);
                    }
                    break;
                case REQUEST_WX_USER_INFO:
                    Log.e("获取微信个人信息",msg.obj.toString());
                    WXUserInfo wxUserInfo=new Gson().fromJson(msg.obj.toString(),WXUserInfo.class);
                    if (!TextUtils.isEmpty(wxUserInfo.getUnionid())){
                        toThirdLogin(wxUserInfo);
                    }else{
                        ToastUtil.showToast("获取微信个人信息失败");
                        finish();
                    }
                    break;
            }

        }
    };

    private void toThirdLogin(WXUserInfo wxUserInfo) {
        RequestParams params=new RequestParams(BaseUrl.getInstence().getThirdLoginUrl());
        params.addParameter("openid",wxUserInfo.getUnionid());//
        params.addParameter("type","WX");
        params.addParameter("app",1);
        params.addParameter("user_name",wxUserInfo.getNickname());
        params.addParameter("avatar",wxUserInfo.getHeadimgurl());
        HttpRequestUtils httpRequestUtils=new HttpRequestUtils(sHandler);
        httpRequestUtils.doPost(params);
    }

    Handler sHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REQUEST_SUCCESS:
                    Log.e("微信登录本地服务器获取数据",msg.obj.toString());
                    ThirdLoginResult mThirdLoginResult=new Gson().fromJson(msg.obj.toString(),ThirdLoginResult.class);
                    if (mThirdLoginResult!=null&&mThirdLoginResult.getData()!=null){
                        PreferenceUtils.getInstance().setLoginStatus(true);
                        PreferenceUtils.getInstance().setUserName(mThirdLoginResult.getData().getUser_login());
                        PreferenceUtils.getInstance().setUserToken(mThirdLoginResult.getData().getToken());
                        PreferenceUtils.getInstance().setLoginType(mThirdLoginResult.getData().getLogin_type());
                        PreferenceUtils.getInstance().setThirdLoginType(mThirdLoginResult.getData().getThird_type());
                        PreferenceUtils.getInstance().setOpenId(mThirdLoginResult.getData().getOpenid());
                        MainActivity.actionStart(WXEntryActivity.this);
                        finish();
                    }else{
                        ToastUtil.showToast("微信登录失败");
                        finish();
                    }
                    break;
                case REQUEST_FAIL:
                    ToastUtil.showToast(msg.obj.toString());
                    finish();
                    break;
            }
        }
    };
}
