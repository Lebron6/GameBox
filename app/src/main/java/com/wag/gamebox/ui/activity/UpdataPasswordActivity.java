package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.LoginResult;
import com.wag.gamebox.entity.UpLoadIcon;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by James on 2018/10/10.
 */
public class UpdataPasswordActivity extends BaseActivity {

    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_re_new_password)
    EditText etReNewPassword;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UpdataPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger=TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("修改密码");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_updata_password;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        String oldPassWord=etOldPassword.getText().toString().trim();
        String newpPassword=etNewPassword.getText().toString().trim();
        String reNewPassword=etReNewPassword.getText().toString().trim();
        if (TextUtils.isEmpty(oldPassWord)){
            ToastUtil.showToast("请输入原密码");
            return;
        }
        if (TextUtils.isEmpty(newpPassword)){
            ToastUtil.showToast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(reNewPassword)){
            ToastUtil.showToast("请输入确认密码");
            return;
        }
        if (oldPassWord.equals(newpPassword)||oldPassWord.equals(reNewPassword)){
            ToastUtil.showToast("新密码不能与旧密码相同");
            return;
        }
        if (newpPassword.length()<6){
            ToastUtil.showToast("密码长度应大于六位数");
            return;
        }
        if (newpPassword.length()>22){
            ToastUtil.showToast("密码长度过长");
            return;
        }
        if (!newpPassword.equals(reNewPassword)){
            ToastUtil.showToast("新密码与确认密码不一致");
            return;
        }
        upDataPassword(oldPassWord,newpPassword,reNewPassword);
    }

    private void upDataPassword(String oldPassWord, String newpPassword, String reNewPassword) {
        tvCommit.setEnabled(false);
        RequestParams params = new RequestParams(BaseUrl.getInstence().getFindLoginPassUrl());
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        params.addParameter("oldPassword", oldPassWord);
        params.addParameter("newPassword",newpPassword);
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils(mHandler);
        httpRequestUtils.doPost(params);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            tvCommit.setEnabled(true);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取修改结果", msg.obj.toString());
                    UpLoadIcon result = new Gson().fromJson(msg.obj.toString(), UpLoadIcon.class);
                    if (result.getCode().equals("200")) {
                      ToastUtil.showToast("修改成功");
                        finish();
                    } else {
                        ToastUtil.showToast(result.getMsg());
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast(msg.obj.toString());
                    break;
            }
        }
    };

}
