package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.Servicer;
import com.wag.gamebox.entity.Voucher;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.adapter.VoucherListAdapter;
import com.wag.gamebox.ui.dialog.CallServiceDialog;
import com.wag.gamebox.ui.view.LoadingManger;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by James on 2018/10/29.
 */
public class VoucherActivity extends BaseActivity {


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
    @BindView(R.id.rv_voucher)
    RecyclerView rvVoucher;
    VoucherListAdapter adapter;
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

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, VoucherActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("我的代金券");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_voucher;
    }

    @Override
    protected void initViews() {
        adapter = new VoucherListAdapter(this);
        getVouchList();
        LoadingManger.getInsetance().setView(error);
        LoadingManger.getInsetance().startLoading();
    }

    @Override
    protected void initDatas() {

    }


    private void getVouchList() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getVoucherListUrl());
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
                    Log.e("获取优惠券列表", msg.obj.toString());
                    Voucher servicer = new Gson().fromJson(msg.obj.toString(), Voucher.class);
                    if (servicer.getData() != null && servicer.getData().size() > 0) {
                        adapter.setDatas(servicer);
                        RecyclerViewHelper.initRecyclerViewV(VoucherActivity.this, rvVoucher, false, adapter, true);
                        LoadingManger.getInsetance().stopFinishLoading(true);
                    } else {
                        LoadingManger.getInsetance().stopFinishLoading("暂无代金券内容", false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
                    Log.e("获取代金券内容", msg.obj.toString());
                    break;
            }
        }
    };

}
