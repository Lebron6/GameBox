package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.HomeData;
import com.wag.gamebox.entity.MoreHotGame;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.Utils;
import com.wag.gamebox.ui.adapter.HotGameListAdapter;
import com.wag.gamebox.ui.view.LoadingManger;
import com.wag.gamebox.ui.view.SimpleFooter;
import com.wag.gamebox.ui.view.SimpleHeader;
import com.wag.gamebox.ui.view.SpringView;
import com.wag.gamebox.ui.view.TitleBarManger;
import com.wag.gamebox.ui.view.effect.EffectConstants;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by James on 2018/10/8.
 */
public class HotGameCenterActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_sreach)
    ImageView ivSreach;
    @BindView(R.id.rv_hot_game)
    RecyclerView rvHotGame;
    @BindView(R.id.sv_hot_game)
    SpringView svHotGame;
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
    @BindView(R.id.sx)
    RelativeLayout sx;
    private HotGameListAdapter adapter;
    private int page=1;
    private MoreHotGame mHotGame;


    @Override
    protected void initTitle() {
        TitleBarManger manger=TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("热门游戏");
        manger.setBack();
        manger.setSearch();
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

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HotGameCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_hot_game;
    }

    @Override
    protected void initViews() {
        LoadingManger insetance = LoadingManger.getInsetance();
        insetance.setView(findViewById(R.id.error));
        initSpringViewStyle();
        adapter = new HotGameListAdapter(this);
    }

    @Override
    protected void initDatas() {
        getHotGameList();
    }

    private void initSpringViewStyle() {
        svHotGame.setType(SpringView.Type.FOLLOW);
        svHotGame.setListener(onFreshListener);
        svHotGame.setHeader(new SimpleHeader(this));
        svHotGame.setFooter(new SimpleFooter(this));
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            getHotGameList();
        }

        @Override
        public void onLoadmore() {
            onLoadmoreList();
        }
    };

    public void getHotGameList(){
        LoadingManger.getInsetance().startLoading();
        HttpRequestUtils requestUtils=new HttpRequestUtils(mHandler);
        requestUtils.doGet(new RequestParams(BaseUrl.getInstence().getHotGameListUrl()));
    }

    private void onLoadmoreList() {
        page=++page;
        RequestParams params=new RequestParams(BaseUrl.getInstence().getHotGameListUrl());
        params.addParameter("page",page);
        HttpRequestUtils requestUtils=new HttpRequestUtils(sHandler);
        requestUtils.doGet(params);
    }


    private android.os.Handler mHandler=new android.os.Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            svHotGame.onFinishFreshAndLoad();
            switch (msg.what)
            {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取热门更多数据",msg.obj.toString());
                    try {
                        mHotGame = new Gson().fromJson(msg.obj.toString(),MoreHotGame.class);
                        if (mHotGame!=null&& mHotGame.getData()!=null&& mHotGame.getData().size()>0){
                            adapter.setDatas(mHotGame.getData());
                            RecyclerViewHelper.initRecyclerViewV(HotGameCenterActivity.this,rvHotGame,false, adapter,false);
                            adapter.setOnItemClickListener(new HotGameListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemLongClick(View view, int position) {
                                    GameDeatailsActivity.actionStart(HotGameCenterActivity.this, mHotGame.getData().get(position).getId());
                                }
                            });
                            LoadingManger.getInsetance().stopFinishLoading(true);
                        }else{
                            LoadingManger.getInsetance().stopFinishLoading("暂无游戏数据",false);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        LoadingManger.getInsetance().stopFinishLoading("数据解析异常",false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常",false);
                    Log.e("获取热门游戏数据",msg.obj.toString());
                    break;
            }
        }
    };

    private android.os.Handler sHandler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            svHotGame.onFinishFreshAndLoad();
            switch (msg.what)
            {
                case Constant.REQUEST_SUCCESS:
                    Log.e("加载热门更多数据",msg.obj.toString());
                    try {
                        final MoreHotGame hotGame=new Gson().fromJson(msg.obj.toString(),MoreHotGame.class);
                        if (hotGame!=null&&hotGame.getData()!=null&&hotGame.getData().size()>0){
                            mHotGame.getData().addAll(hotGame.getData());
                            adapter.setDatas(mHotGame.getData());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        ToastUtil.showToast("解析数据异常");
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("加载热门游戏数据",msg.obj.toString());
                    break;
            }
        }
    };

    @OnClick({R.id.iv_back, R.id.iv_sreach})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_sreach:
                break;
        }
    }
}
