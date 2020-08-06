package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
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
import com.wag.gamebox.callback.GetVoucherResultCallBack;
import com.wag.gamebox.entity.TaskList;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.adapter.TaskListAdapter;
import com.wag.gamebox.ui.view.LoadingManger;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by James on 2018/10/29.
 */
public class TaskActivity extends BaseActivity implements GetVoucherResultCallBack {


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

    TaskListAdapter adapter;
    @BindView(R.id.rv_task)
    RecyclerView rvTask;
    @BindView(R.id.msg)
    TextView msg;
    @BindView(R.id.res)
    LinearLayout res;
    @BindView(R.id.loading)
    ImageView loading;
    @BindView(R.id.layout_loading)
    LinearLayout layoutLoading;
    @BindView(R.id.error)
    RelativeLayout error;
    private TaskList taskList;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("任务中心");
        manger.setBack();
        getTask();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_task;
    }

    @Override
    protected void initViews() {
        adapter = new TaskListAdapter(this);

        LoadingManger.getInsetance().setView(error);
        LoadingManger.getInsetance().startLoading();
    }

    @Override
    protected void initDatas() {

    }


    private void getTask() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getTaskListUrl());
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        HttpRequestUtils requestUtils = new HttpRequestUtils(cHandler);
        requestUtils.doGet(params);
    }

    private Handler cHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取任务", msg.obj.toString());
                    taskList = new Gson().fromJson(msg.obj.toString(), TaskList.class);
                    if (taskList.getData() != null && taskList.getData().size() > 0) {
                        adapter.setDatas(taskList, TaskActivity.this);
                        adapter.setOnItemClickListener(onItemClickListener);
                        RecyclerViewHelper.initRecyclerViewV(TaskActivity.this, rvTask, false, adapter, true);
                        LoadingManger.getInsetance().stopFinishLoading(true);
                    } else {
                        LoadingManger.getInsetance().stopFinishLoading("暂无任务内容", false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
                    Log.e("获取任务内容", msg.obj.toString());
                    break;
            }
        }
    };

    TaskListAdapter.OnItemClickListener onItemClickListener=new TaskListAdapter.OnItemClickListener() {
        @Override
        public void onItemLongClick(View view, int position) {
            TaskDetailsActivity.actionStart(TaskActivity.this,"任务详情",taskList.getData().get(position).getId());
        }
    };

    @Override
    public void voucherResult(int i) {
        ToastUtil.showToast("领取成功,请在优惠券中心查看");
        getTask();
    }

}
