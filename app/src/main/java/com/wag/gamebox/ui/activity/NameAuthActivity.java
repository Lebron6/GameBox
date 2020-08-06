package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.NameAuthInfo;
import com.wag.gamebox.entity.NameAuthResult;
import com.wag.gamebox.tools.IdCardTool;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by James on 2018/10/9.
 */
public class NameAuthActivity extends BaseActivity {


    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.layout_back)
    LinearLayout layoutBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_sreach)
    ImageView ivSreach;
    @BindView(R.id.layout_top)
    RelativeLayout layoutTop;
    @BindView(R.id.et_real_name)
    EditText etRealName;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NameAuthActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("实名认证");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_name_auth;
    }

    @Override
    protected void initViews() {
        getNameAuthInfo();
    }

    @Override
    protected void initDatas() {

    }


    private void getNameAuthInfo() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getNameAuthInfoUrl());
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils(rHandler);
        httpRequestUtils.doGet(params);
    }


    Handler rHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("认证信息", msg.obj.toString());
                    try {
                        NameAuthInfo nameAuthInfo = new Gson().fromJson(msg.obj.toString(), NameAuthInfo.class);
                        if (nameAuthInfo.getCode().equals("200")) {
                            if (nameAuthInfo.getData().getStatus() == 2) {
                                etIdNumber.setText(nameAuthInfo.getData().getIdcard());
                                etRealName.setText(nameAuthInfo.getData().getName());
                                etRealName.setEnabled(false);
                                etIdNumber.setEnabled(false);
                                tvCommit.setVisibility(View.GONE);
                            }else{
                                etRealName.setEnabled(true);
                                etIdNumber.setEnabled(true);
                                tvCommit.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast(msg.obj.toString());
                    break;
            }
        }
    };


    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etRealName.getText().toString())) {
            ToastUtil.showToast("请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(etIdNumber.getText().toString())) {
            ToastUtil.showToast("请输入身份证号码");
            return;
        }
        if (IdCardTool.validateCard(etIdNumber.getText().toString())==false){
            ToastUtil.showToast("身份证格式有误");
            return;
        }
        toNameAuth();
    }

    private void toNameAuth() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getNameAuthUrl());
        params.addHeader("token",PreferenceUtils.getInstance().getUserToken());
        params.addParameter("name", etRealName.getText().toString());
        params.addParameter("idcard", etIdNumber.getText().toString());
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils(mHandler);
        httpRequestUtils.doPost(params);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("实名认证", msg.obj.toString());
                    try {
                        NameAuthResult nameAuthResult = new Gson().fromJson(msg.obj.toString(), NameAuthResult.class);
                        if (nameAuthResult.getCode().equals("200")) {
                           finish();
                        }
                        ToastUtil.showToast(nameAuthResult.getMsg());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast(msg.obj.toString());
                    break;
            }
        }
    };
}
