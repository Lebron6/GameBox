package com.wag.gamebox.ui.fragment;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.MyPage;
import com.wag.gamebox.entity.Servicer;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.Utils;
import com.wag.gamebox.ui.activity.LoginActivity;
import com.wag.gamebox.ui.activity.MyCommentActivity;
import com.wag.gamebox.ui.activity.MyGameActivity;
import com.wag.gamebox.ui.activity.MyGiftActivity;
import com.wag.gamebox.ui.activity.MyPTBActivity;
import com.wag.gamebox.ui.activity.TaskActivity;
import com.wag.gamebox.ui.activity.UserInfoActivity;
import com.wag.gamebox.ui.activity.VipRightActivity;
import com.wag.gamebox.ui.activity.VoucherActivity;
import com.wag.gamebox.ui.dialog.CallServiceDialog;

import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by James on 2019/5/21.
 */
public class My extends BaseFragment {
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_to_login)
    TextView tvToLogin;
    @BindView(R.id.tv_vip_level)
    TextView tvVipLevel;
    @BindView(R.id.progress_vip)
    ProgressBar progressVip;
    @BindView(R.id.tv_vip_process)
    TextView tvVipProcess;
    @BindView(R.id.tv_see_th)
    TextView tvSeeTh;
    @BindView(R.id.layout_vip)
    LinearLayout layoutVip;
    @BindView(R.id.tv_yhj_num)
    TextView tvYhjNum;
    @BindView(R.id.layout_yhj)
    LinearLayout layoutYhj;
    @BindView(R.id.tv_ptb_num)
    TextView tvPtbNum;
    @BindView(R.id.layout_ptb)
    LinearLayout layoutPtb;
    @BindView(R.id.layout_my_gift)
    LinearLayout layoutMyGift;
    @BindView(R.id.layout_my_comment)
    LinearLayout layoutMyComment;
    @BindView(R.id.layout_call_service)
    LinearLayout layoutCallService;
    @BindView(R.id.layout_p_nomal)
    LinearLayout layoutPNomal;
    Unbinder unbinder;
    @BindView(R.id.tv_task_num)
    TextView tvTaskNum;
    @BindView(R.id.layout_my_game)
    LinearLayout layoutMyGame;
    Unbinder unbinder1;
    @BindView(R.id.layout_task)
    LinearLayout layoutTask;
    private MyPage myPage;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        if (!PreferenceUtils.getInstance().getLoginStatus()) {
            tvToLogin.setText("点击登录");
            layoutPNomal.setVisibility(View.GONE);
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
                        myPage = new Gson().fromJson(msg.obj.toString(), MyPage.class);
                        if (myPage.getData() != null) {
                            tvYhjNum.setText(myPage.getData().getCoupon() + "");
                            tvTaskNum.setText(myPage.getData().getTask() + "");
                            Log.e("huoqu",myPage.getData().getHead_img());
                            ImageOptions imageOptions = (new ImageOptions.Builder()).setImageScaleType(ImageView.ScaleType.CENTER_CROP).setCircular(true).setCrop(true).setIgnoreGif(false).build();
                            x.image().bind(ivUserIcon, myPage.getData().getHead_img(), imageOptions);
                            tvPtbNum.setText(myPage.getData().getWallet());
                            if (!TextUtils.isEmpty(myPage.getData().getUser_name())){
                                tvToLogin.setText(myPage.getData().getUser_name());
                            }else{
                                tvToLogin.setText(myPage.getData().getUser_login());
                            }
                            tvVipLevel.setText("V" + myPage.getData().getVip());
                            tvVipProcess.setText(myPage.getData().getAmount() + "/" + myPage.getData().getMin());
                            progressVip.setProgress(Integer.valueOf(Double.valueOf(myPage.getData().getAmount()) / Double.valueOf(myPage.getData().getMin()) * 100 + ""));
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

    @OnClick({R.id.layout_task, R.id.layout_yhj, R.id.iv_user_icon, R.id.tv_to_login, R.id.tv_see_th, R.id.layout_ptb, R.id.layout_my_gift, R.id.layout_my_comment, R.id.layout_call_service, R.id.layout_my_game})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    UserInfoActivity.actionStart(getActivity());
                }
                break;
            case R.id.tv_to_login:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    return;
                }
                break;
            case R.id.tv_see_th:
                VipRightActivity.actionStart(getActivity());
                break;
            case R.id.layout_task:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    TaskActivity.actionStart(getActivity());
                }
                break;
            case R.id.layout_yhj:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    VoucherActivity.actionStart(getActivity());
                }
                break;
            case R.id.layout_ptb:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    MyPTBActivity.actionStart(getActivity());
                }
                break;
            case R.id.layout_my_gift:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    MyGiftActivity.actionStart(getActivity());
                }
                break;
            case R.id.layout_my_comment:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    MyCommentActivity.actionStart(getActivity());
                }
                break;
            case R.id.layout_call_service:

                if (Utils.isQQInstall(getContext())) {
                    final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + 1437835416;
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                } else {
                    ToastUtil.showToast("请安装QQ客户端");
                }

//                if (!TextUtils.isEmpty(myPage.getData().getService_tel())) {
//                    CallServiceDialog dialog = new CallServiceDialog(getActivity(), R.style.MyDialog, myPage.getData().getService_tel());
//                    dialog.show();
//                } else {
//                    ToastUtil.showToast("获取客服热线失败");
//                }
//                getServicerPhoneNum();
                break;
            case R.id.layout_my_game:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    MyGameActivity.actionStart(getActivity());
                }
                break;
        }
    }

    private void getServicerPhoneNum() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getCallServiceUrl());
        HttpRequestUtils requestUtils = new HttpRequestUtils(cHandler);
        requestUtils.doGet(params);
    }

    private Handler cHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取客服热线", msg.obj.toString());
                    Servicer servicer = new Gson().fromJson(msg.obj.toString(), Servicer.class);
                    if (!TextUtils.isEmpty(servicer.getData())) {
                        CallServiceDialog dialog = new CallServiceDialog(getActivity(), R.style.MyDialog, servicer.getData());
                        dialog.show();
                    } else {
                        ToastUtil.showToast("获取客服热线失败");
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast("获取客服热线失败");
                    Log.e("获取客服热线失败", msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
