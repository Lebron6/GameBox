package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.wag.gamebox.entity.MyGame;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.ui.adapter.HotGameListAdapter;
import com.wag.gamebox.ui.view.LoadingManger;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by James on 2018/9/29.
 */
public class MyGameActivity extends BaseActivity {

    @BindView(R.id.rv_mygame)
    RecyclerView rvMygame;
    @BindView(R.id.error)
    RelativeLayout error;
    private HotGameListAdapter adapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyGameActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("我的游戏");
        manger.setBack();
        if (adapter != null) {
            adapter.start();
            adapter.state();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        if (adapter != null) {
            adapter.delete();
        }
        super.onPause();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_game;
    }

    @Override
    protected void initViews() {
        adapter = new HotGameListAdapter(this);
        LoadingManger.getInsetance().setView(error);
    }


    @Override
    protected void initDatas() {
        getGameList();
    }

    public void getGameList() {
        LoadingManger.getInsetance().startLoading();
        RequestParams params = new RequestParams(BaseUrl.getInstence().getMyGameUrl());
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
                    Log.e("获取我的游戏数据", msg.obj.toString());
                    try {
                        final MyGame myGame = new Gson().fromJson(msg.obj.toString(), MyGame.class);
                        if (myGame != null && myGame.getData() != null && myGame.getData().size() > 0) {
                            adapter.setDatas(myGame.getData());
                            RecyclerViewHelper.initRecyclerViewV(MyGameActivity.this, rvMygame, true, adapter,true);
                            adapter.setOnItemClickListener(new HotGameListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemLongClick(View view, int position) {
                                    GameDeatailsActivity.actionStart(MyGameActivity.this, myGame.getData().get(position).getId());
                                }
                            });
                            LoadingManger.getInsetance().stopFinishLoading(true);
                        } else {
                            LoadingManger.getInsetance().stopFinishLoading("未在平台玩过游戏", false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LoadingManger.getInsetance().stopFinishLoading("数据解析异常", false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
                    Log.e("获取我的游戏数据", msg.obj.toString());
                    break;
            }
        }
    };

}