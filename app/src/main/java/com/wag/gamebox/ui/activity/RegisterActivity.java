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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.GetPhoneCodeResult;
import com.wag.gamebox.entity.RegisterResult;
import com.wag.gamebox.tools.IdCardTool;
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
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_sreach)
    ImageView ivSreach;
    @BindView(R.id.layout_top)
    RelativeLayout layoutTop;
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
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.layout_back)
    LinearLayout layoutBack;
    @BindView(R.id.et_real_name)
    EditText etRealName;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("注册");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }


    @OnClick({R.id.tv_get_phone_code, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_phone_code:
                if (TextUtils.isEmpty(etAccount.getText().toString())) {
                    ToastUtil.showToast("请输入手机号码");
                    return;
                }
//                if (!Utils.isMobileNO(etAccount.getText().toString())) {
//                    ToastUtil.showToast("手机号码格式错误");
//                    return;
//                }
                getPhoneCode(etAccount.getText().toString());
                break;
            case R.id.tv_register:
                if (TextUtils.isEmpty(etAccount.getText().toString())) {
                    ToastUtil.showToast("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(etPhoneCode.getText().toString())) {
                    ToastUtil.showToast("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    ToastUtil.showToast("请输入密码");
                    return;
                }
                if (etPassword.getText().toString().trim().length() < 6) {
                    ToastUtil.showToast("密码长度应不小于六位");
                    return;
                }
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
                toRegister();
                break;
        }
    }

    private void getPhoneCode(String phoneNum) {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getPhoneCodeUrl());
        params.addParameter("mobile", phoneNum);
        params.addParameter("type", "REGISTER");
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils(mHandler);
        httpRequestUtils.doPost(params);
    }

    private void toRegister() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getAccountRegisterUrl());
        params.addParameter("mobile", etAccount.getText().toString());
        params.addParameter("code", etPhoneCode.getText().toString());
        params.addParameter("user_pass", etPassword.getText().toString());
        params.addParameter("name", etRealName.getText().toString());
        params.addParameter("type", "APP");
        params.addParameter("idcard", etIdNumber.getText().toString());
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils(rHandler);
        httpRequestUtils.doPost(params);
    }

    Handler rHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("注册结果", msg.obj.toString());
                    try {
                        RegisterResult result = new Gson().fromJson(msg.obj.toString(), RegisterResult.class);
                        if (result.getCode().equals("200")) {
                            finish();
                        }
                        ToastUtil.showToast(result.getMsg());
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

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    try {
                        Log.e("获取注册验证码", msg.obj.toString());
                        GetPhoneCodeResult result = new Gson().fromJson(msg.obj.toString(), GetPhoneCodeResult.class);
                        if (result.getCode().equals("200")) {
                            TimeCount time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
                            time.start();// 开始计时
                            ToastUtil.showToast("验证码已发送，注意查收");
                        } else {
                            ToastUtil.showToast(result.getMsg());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("数据解析异常", e.toString());
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast(msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

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

            } catch (Exception e) {

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
            } catch (Exception E) {
            }

        }

    }
}
