package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.GetGiftResult;
import com.wag.gamebox.entity.GetVoucher;
import com.wag.gamebox.entity.GiftDetails;
import com.wag.gamebox.entity.TaskDetails;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.Utils;
import com.wag.gamebox.ui.dialog.GetGiftSuccessDialog;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by James on 2018/10/8.
 */
public class TaskDetailsActivity extends BaseActivity {


    public static final String TASK_ID = "gift_id";
    public static final String TITLE = "title";
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
    @BindView(R.id.tv_task_name)
    TextView tvTaskName;
    @BindView(R.id.tv_task_details)
    TextView tvTaskDetails;
    @BindView(R.id.tv_task_time)
    TextView tvTaskTime;
    @BindView(R.id.tv_get)
    TextView tvGet;

    public static void actionStart(Context context, String title, int taskId) {
        Intent intent = new Intent(context, TaskDetailsActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(TASK_ID, taskId);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle(getIntent().getStringExtra(TITLE));
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_task_details;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

        getTaskInfo();


    }

    private void getTaskInfo() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getTaskDetailsUrl());

        params.addParameter("id", getIntent().getIntExtra(TASK_ID, -1) + "");
        if (PreferenceUtils.getInstance().getLoginStatus() == true) {
            params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        }
        Log.e("details", getIntent().getIntExtra(TASK_ID, -1) + "");
        Log.e("token", PreferenceUtils.getInstance().getUserToken());
        HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
        requestUtils.doGet(params);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取任务详情数据", msg.obj.toString());
                    TaskDetails taskDetails=new Gson().fromJson(msg.obj.toString(),TaskDetails.class);
                    if (taskDetails!=null&&taskDetails.getData()!=null){
                        tvTaskName.setText(taskDetails.getData().getName()+"");
                        tvTaskDetails.setText(taskDetails.getData().getExplain()+"");
                        tvTaskTime.setText(taskDetails.getData().getStart_time()+"-----"+taskDetails.getData().getEnd_time());
//                        tvTaskDetails.setText(taskDetails.getData().getExplain()+"");
                        if (taskDetails.getData().getStatus()==1){
tvGet.setText("待完成");
tvGet.setEnabled(false);
                        }else if(taskDetails.getData().getStatus()==2){
                            tvGet.setText("领取奖励"); tvGet.setEnabled(true);
                        }else{
                            tvGet.setText("已领取"); tvGet.setEnabled(false);
                        }
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("获取任务详情数据", msg.obj.toString());
                    break;
            }
        }
    };


//    private void get() {
//        RequestParams params = new RequestParams(BaseUrl.getInstence().getReceiveGiftUrl());
//        params.addParameter("kac_id", getIntent().getIntExtra(GIFT_ID, -1));
//        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
//        HttpRequestUtils requestUtils = new HttpRequestUtils(sHandler);
//        requestUtils.doGet(params);
//    }


    @OnClick(R.id.tv_get)
    public void onViewClicked() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getVoucherUrl());
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        params.addParameter("id",getIntent().getIntExtra(TASK_ID,-1));
        HttpRequestUtils requestUtils = new HttpRequestUtils(cHandler);
        requestUtils.doGet(params);
    }
    private Handler cHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("领取奖励", msg.obj.toString());
                    GetVoucher servicer = new Gson().fromJson(msg.obj.toString(), GetVoucher.class);
                    if (servicer!=null){
                        if (servicer.getCode().equals("200")){
                           ToastUtil.showToast("领取奖励成功");
                           finish();
                        }else{
                            ToastUtil.showToast(servicer.getMsg());
                        }
                    }else{
                        ToastUtil.showToast("领取奖励失败");
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast("领取奖励失败");
                    Log.e("领取奖励失败", msg.obj.toString());
                    break;
            }
        }
    };
}
