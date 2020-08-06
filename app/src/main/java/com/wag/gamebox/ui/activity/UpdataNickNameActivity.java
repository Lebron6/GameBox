package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
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
import com.wag.gamebox.entity.UpLoadIcon;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by James on 2018/10/10.
 */
public class UpdataNickNameActivity extends BaseActivity {


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
    @BindView(R.id.et_re_new_nickname)
    EditText etReNewNickname;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UpdataNickNameActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("修改昵称");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_updata_nickname;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
       String userNickName=etReNewNickname.getText().toString();
       if (!TextUtils.isEmpty(userNickName)){
           upDataNickName(userNickName);
       }else{
           ToastUtil.showToast("请输入需修改昵称");
       }

    }

    private void upDataNickName(String nickName) {
        tvCommit.setEnabled(false);
        RequestParams params = new RequestParams(BaseUrl.getInstence().getUpdataNickNameUrl());
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        params.addParameter("name", nickName);
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
