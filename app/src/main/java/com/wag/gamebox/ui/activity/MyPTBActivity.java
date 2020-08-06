package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.MyPTB;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.adapter.PTBListAdapter;
import com.wag.gamebox.ui.view.LoadingManger;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by James on 2018/10/9.
 */
public class MyPTBActivity extends BaseActivity {

    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.layout_top)
    RelativeLayout layoutTop;
    @BindView(R.id.tv_buy_ptb)
    TextView tvBuyPtb;
    @BindView(R.id.rv_buy_list)
    RecyclerView rvBuyList;
    @BindView(R.id.tv_ptb_num)
    TextView tvPtbNum;
    private PTBListAdapter adapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyPTBActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("我的平台币");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_ptb;
    }

    @Override
    protected void initViews() {
        adapter = new PTBListAdapter(this);
    }

    @Override
    protected void initDatas() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getMyPTBUrl());
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
        requestUtils.doGet(params);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取我的平台币数据", msg.obj.toString());
                    MyPTB myPTB = new Gson().fromJson(msg.obj.toString(), MyPTB.class);
                    if (myPTB != null && myPTB.getData() != null) {
                        if (myPTB.getData().getUser() != null) {
                            tvPtbNum.setText(myPTB.getData().getUser().getWallet());
                        }
                        if (myPTB.getData().getList() != null && myPTB.getData().getList().size() > 0) {
                            adapter.setDatas(myPTB);
                            RecyclerViewHelper.initRecyclerViewV(MyPTBActivity.this, rvBuyList, true, adapter);
                        }else{
//                            ToastUtil.showToast("暂无充值或消费记录");
                        }
                    }

                    break;
                case Constant.REQUEST_FAIL:
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
                    Log.e("获取我的平台币数据", msg.obj.toString());
                    break;
            }
        }
    };

    @OnClick(R.id.tv_buy_ptb)
    public void onViewClicked() {
        BuyPTBActivity.actionStart(this);
    }

}
