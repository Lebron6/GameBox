package com.wag.gamebox.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.MyPage;
import com.wag.gamebox.entity.VersionInfo;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.Utils;
import com.wag.gamebox.tools.VersionUpdataUtils;
import com.wag.gamebox.ui.activity.AboutUsActivity;
import com.wag.gamebox.ui.activity.LoginActivity;
import com.wag.gamebox.ui.activity.MyGameActivity;
import com.wag.gamebox.ui.activity.MyGiftActivity;
import com.wag.gamebox.ui.activity.MyPTBActivity;
import com.wag.gamebox.ui.activity.ShareAppActivity;
import com.wag.gamebox.ui.activity.UserInfoActivity;
import com.wag.gamebox.ui.activity.VipRightActivity;
import com.wag.gamebox.ui.dialog.CleanCacheDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by LeBron on 2017/2/6.
 */

public class Mcenter extends BaseFragment {

    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_to_login)
    TextView tvToLogin;
    @BindView(R.id.layout_user_info)
    LinearLayout layoutUserInfo;
    @BindView(R.id.tv_nomal_myptb)
    TextView tvNomalMyptb;
    @BindView(R.id.tv_ptb_num)
    TextView tvPtbNum;
    @BindView(R.id.tv_to_buy_ptb)
    TextView tvToBuyPtb;
    @BindView(R.id.layout_my_game)
    LinearLayout layoutMyGame;
    @BindView(R.id.layout_my_gifts)
    LinearLayout layoutMyGifts;
    @BindView(R.id.layout_clear)
    LinearLayout layoutClear;
    @BindView(R.id.layout_updata)
    LinearLayout layoutUpdata;
    @BindView(R.id.layout_share_app)
    LinearLayout layoutShareApp;
    @BindView(R.id.layout_call_service)
    LinearLayout layoutCallService;
    @BindView(R.id.layout_ptb)
    RelativeLayout layoutPtb;
    @BindView(R.id.progress_vip)
    ProgressBar progressVip;
    @BindView(R.id.tv_vip_process)
    TextView tvVipProcess;
    @BindView(R.id.tv_see_th)
    TextView tvSeeTh;
    @BindView(R.id.layout_vip)
    LinearLayout layoutVip;
    Unbinder unbinder;
    @BindView(R.id.tv_vip_level)
    TextView tvVipLevel;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mcenter;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        if (!PreferenceUtils.getInstance().getLoginStatus()) {
            tvToLogin.setText("点击登录");
            layoutPtb.setVisibility(View.GONE);
            layoutVip.setVisibility(View.GONE);
        } else {
            RequestParams params = new RequestParams(BaseUrl.getInstence().getMyPageUrl());
            params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
            HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
            requestUtils.doGet(params);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取个人信息数据", msg.obj.toString());
                    try {
                        MyPage myPage = new Gson().fromJson(msg.obj.toString(), MyPage.class);
                        if (myPage.getData() != null) {
                            ImageOptions imageOptions = (new ImageOptions.Builder()).setImageScaleType(ImageView.ScaleType.CENTER_CROP).setCircular(true).setCrop(true).setIgnoreGif(false).build();
                            x.image().bind(ivUserIcon, myPage.getData().getHead_img(), imageOptions);
                            tvPtbNum.setText(myPage.getData().getWallet());
                            tvToLogin.setText(myPage.getData().getUser_login());
                            tvVipLevel.setText("V"+myPage.getData().getVip());
                            tvVipProcess.setText(myPage.getData().getAmount()+"/"+myPage.getData().getMin());
                            Log.e("111",Integer.valueOf(myPage.getData().getAmount())+"");
                            Log.e("222",Integer.valueOf(myPage.getData().getMin())+"");
                            progressVip.setProgress(Integer.valueOf(Double.valueOf(myPage.getData().getAmount())/Double.valueOf(myPage.getData().getMin())*100+""));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("解析个人信息数据失败", msg.obj.toString());
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    PreferenceUtils.getInstance().loginOut();
                    LoginActivity.actionStart(getActivity());
                    getActivity().finish();
                    ToastUtil.showToast("登录信息过期，请重新登录");
//                    Log.e("获取个人信息失败", msg.obj.toString());
                    break;
            }
        }
    };

    @OnClick({R.id.tv_see_th,R.id.iv_user_icon, R.id.tv_to_login, R.id.tv_to_buy_ptb, R.id.layout_my_game, R.id.layout_my_gifts, R.id.layout_clear, R.id.layout_updata, R.id.layout_share_app, R.id.layout_call_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_see_th:
                VipRightActivity.actionStart(getActivity());
                break;
            case R.id.tv_to_login:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    return;
                }
                break;
            case R.id.tv_to_buy_ptb:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    MyPTBActivity.actionStart(getActivity());
                }
                break;
            case R.id.layout_my_game:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    MyGameActivity.actionStart(getActivity());
                }
                break;
            case R.id.layout_my_gifts:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    MyGiftActivity.actionStart(getActivity());
                }
                break;
            case R.id.layout_clear:
                CleanCacheDialog cleanCacheDialog = new CleanCacheDialog(getActivity(), R.style.MyDialog);
                cleanCacheDialog.show();
                break;
            case R.id.layout_updata:
                checkVersionInformation();
                break;
            case R.id.layout_share_app:
                ShareAppActivity.actionStart(getActivity());
                break;
            case R.id.layout_call_service:
                AboutUsActivity.actionStart(getActivity());
                break;
            case R.id.iv_user_icon:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    UserInfoActivity.actionStart(getActivity());
                }
                break;
        }
    }

    private void checkVersionInformation() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getVersionsUrl());
        params.addParameter("versions", Utils.getLocalVersion(getActivity()));
        params.addParameter("type", "android");
        HttpRequestUtils requestUtils = new HttpRequestUtils(sHandler);
        requestUtils.doGet(params);
    }


    private Handler sHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取版本数据", msg.obj.toString());
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(msg.obj.toString());
                        if (jsonObject.getString("code").equals("200")) {
                            ToastUtil.showToast("已是最新版本,无需下载");
                        } else if (jsonObject.getString("code").equals("300")) {
                            VersionInfo info = new Gson().fromJson(msg.obj.toString(), VersionInfo.class);
                            if (TextUtils.isEmpty(info.getData().getAndroid_url())) {
                                ToastUtil.showToast("新版本更新链接有误！");
                                return;
                            }
                            VersionUpdataUtils.update(info.getData().getAndroid_url(), getActivity(), 1);
                        } else {
                            ToastUtil.showToast("无效响应Code:" + jsonObject.getString("code"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast("获取版本数据失败");
                    Log.e("获取版本数据", msg.obj.toString());
                    break;
            }
        }
    };


}