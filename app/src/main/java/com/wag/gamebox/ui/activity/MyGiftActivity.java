package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.MyGiftData;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.adapter.MyGiftAdapter;
import com.wag.gamebox.ui.view.LoadingManger;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;

import butterknife.BindView;

/**
 * Created by James on 2018/9/29.
 */
public class MyGiftActivity extends BaseActivity {

    @BindView(R.id.rv_mygift)
    RecyclerView rvMygift;
    @BindView(R.id.error)
    RelativeLayout error;
    private MyGiftAdapter adapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyGiftActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("我的礼包");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_gift;
    }

    @Override
    protected void initViews() {
        adapter = new MyGiftAdapter(MyGiftActivity.this);
    }

    @Override
    protected void initDatas() {
        LoadingManger loadingManger = LoadingManger.getInsetance();
        loadingManger.setView(error);
        LoadingManger.getInsetance().startLoading();
        RequestParams params = new RequestParams(BaseUrl.getInstence().getMyGiftUrl());
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        Log.e("Token",PreferenceUtils.getInstance().getUserToken());
        HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
        requestUtils.doGet(params);
    }

    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取我的礼包数据", msg.obj.toString());
                    try {
                        final MyGiftData myGiftData = new Gson().fromJson(msg.obj.toString(), MyGiftData.class);
                        if (myGiftData != null && myGiftData.getData() != null && myGiftData.getData().size() > 0) {
                            adapter.setDatas(myGiftData);
                            RecyclerViewHelper.initRecyclerViewV(MyGiftActivity.this, rvMygift, true, adapter);
                            adapter.setOnItemClickListener(
                                    new MyGiftAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemLongClick(View view, int position) {
                                            AllGiftOfGameActivity.actionStart(MyGiftActivity.this, myGiftData.getData().get(position).getGame_name(), myGiftData.getData().get(position).getId());
                                        }
                                    }
                            );
                            LoadingManger.getInsetance().stopFinishLoading(true);
                        } else {
                            LoadingManger.getInsetance().stopFinishLoading("暂无礼包领取记录", false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showToast("解析数据异常");
                        LoadingManger.getInsetance().stopFinishLoading("数据解析异常", false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
                    Log.e("获取我的礼包数据", msg.obj.toString());
                    break;
            }
        }
    };

}
