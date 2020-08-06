package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.GetPhoneCodeResult;
import com.wag.gamebox.entity.RegisterResult;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.Utils;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by James on 2018/10/9.
 */
public class ForgetPasswordActivity extends BaseActivity {


    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.txt_code)
    TextView txtCode;
    @BindView(R.id.et_phone_code)
    EditText etPhoneCode;
    @BindView(R.id.tv_get_phone_code)
    TextView tvGetPhoneCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger=TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("忘记密码");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initDatas() {

    }

    @OnClick({R.id.tv_get_phone_code, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_phone_code:
                if (TextUtils.isEmpty(etAccount.getText().toString())) {
                    ToastUtil.showToast("请输入手机号码");
                    return;
                }
                if (!Utils.isMobileNO(etAccount.getText().toString())) {
                    ToastUtil.showToast("手机号码格式错误");
                    return;
                }
                getPhoneCode(etAccount.getText().toString());
                break;
            case R.id.tv_commit:
                if (TextUtils.isEmpty(etAccount.getText().toString())){
                    ToastUtil.showToast("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(etPhoneCode.getText().toString())){
                    ToastUtil.showToast("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(etPassword.getText().toString())){
                    ToastUtil.showToast("请输入密码");
                    return;
                }
                if (etPassword.getText().toString().length()<6){
                    ToastUtil.showToast("密码长度应不小于六位");
                    return;
                }
                toUpdataPassword();
                break;
        }
    }

    private void toUpdataPassword() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getFindPassUrl());
        params.addParameter("mobile", etAccount.getText().toString());
        params.addParameter("code", etPhoneCode.getText().toString());
        params.addParameter("user_pass", etPassword.getText().toString());
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils(rHandler);
        httpRequestUtils.doPost(params);
    }

    Handler rHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("修改结果",msg.obj.toString());
                    try {
                        RegisterResult result=new Gson().fromJson(msg.obj.toString(),RegisterResult.class);
                        if (result.getCode().equals("200")){
                            finish();
                        }
                        ToastUtil.showToast(result.getMsg());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast(msg.obj.toString());
                    break;
            }
        }
    };

    private void getPhoneCode(String phoneNum) {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getPhoneCodeUrl());
        params.addParameter("mobile", phoneNum);
        params.addParameter("type", "FINDPASS");
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils(mHandler);
        httpRequestUtils.doPost(params);
    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    try {
                        Log.e("获取注册验证码",msg.obj.toString());
                        GetPhoneCodeResult result = new Gson().fromJson(msg.obj.toString(), GetPhoneCodeResult.class);
                        if (result.getCode().equals("200")) {
                            ForgetPasswordActivity.TimeCount time = new ForgetPasswordActivity.TimeCount(60000, 1000);// 构造CountDownTimer对象
                            time.start();// 开始计时
                            ToastUtil.showToast("验证码已发送，注意查收");
                        }else{
                            ToastUtil.showToast(result.getMsg());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.e("数据解析异常",e.toString());
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast(msg.obj.toString());
                    break;
            }
        }
    };
    // /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // 计时过程显示
            try {
                tvGetPhoneCode.setEnabled(false);
                tvGetPhoneCode.setClickable(false);
                tvGetPhoneCode.setText(millisUntilFinished / 1000 + "秒");
            }catch (Exception e){

            }

        }

        @Override
        public void onFinish() {// 计时完毕时触发
            try {
                tvGetPhoneCode.setEnabled(true);
                tvGetPhoneCode.setText("重新验证");
                tvGetPhoneCode.setClickable(true);
                tvGetPhoneCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPhoneCode(etAccount.getText().toString());
                    }
                });
            }catch (Exception E){}

        }

    }
}
