package com.wag.gamebox.ui.fragment.classfication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.GameTypeDataList;
import com.wag.gamebox.entity.MoreHotGame;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.activity.GameDeatailsActivity;
import com.wag.gamebox.ui.activity.HotGameCenterActivity;
import com.wag.gamebox.ui.adapter.HotGameListAdapter;
import com.wag.gamebox.ui.fragment.BaseFragment;
import com.wag.gamebox.ui.view.LoadingManger;
import com.wag.gamebox.ui.view.SimpleFooter;
import com.wag.gamebox.ui.view.SimpleHeader;
import com.wag.gamebox.ui.view.SpringView;

import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LeBron on 2017/2/6.
 * 游戏分类
 */
@SuppressLint("ValidFragment")
public class GameType extends BaseFragment {
    @BindView(R.id.game_type_listview)
    RecyclerView gameTypeListview;
    @BindView(R.id.game_type_springview)
    SpringView gameTypeSpringview;
    @BindView(R.id.error)
    RelativeLayout error;
    private int typeId;
    private HotGameListAdapter adapter;
    private int page = 1;

    private GameTypeDataList gameTypeDataList;

    public GameType(int typeId) {
        this.typeId = typeId;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_game_type;
    }

    @Override
    protected void initViews() {
        initSpringViewStyle();
        adapter = new HotGameListAdapter(getActivity());
        RecyclerViewHelper.initRecyclerViewV(getActivity(), gameTypeListview, true, adapter);
    }

    private void initSpringViewStyle() {
        gameTypeSpringview.setType(SpringView.Type.FOLLOW);
        gameTypeSpringview.setListener(onFreshListener);
        gameTypeSpringview.setHeader(new SimpleHeader(getActivity()));
        gameTypeSpringview.setFooter(new SimpleFooter(getActivity()));
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            getTypeGameList();
        }

        @Override
        public void onLoadmore() {
            onLoadMoreList();
        }
    };

    @Override
    protected void initDatas() {
        LoadingManger.getInsetance().setView(error);
        LoadingManger.getInsetance().startLoading();
        getTypeGameList();
        if (adapter != null) {
            adapter.start();
            adapter.state();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {

        super.onPause();
        if (adapter != null) {
            adapter.delete();
        }
    }

    public void getTypeGameList() {
        page=1;
        LoadingManger.getInsetance().startLoading();
        RequestParams params = new RequestParams(BaseUrl.getInstence().getTypeSearchUrl());
        params.addParameter("type_id", typeId);
        HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
        requestUtils.doGet(params);
    }

    private void onLoadMoreList() {
        page = ++page;
        RequestParams params = new RequestParams(BaseUrl.getInstence().getTypeSearchUrl());
        params.addParameter("page", page);
        params.addParameter("type_id", typeId);
        HttpRequestUtils requestUtils = new HttpRequestUtils(sHandler);
        requestUtils.doGet(params);
    }

    private android.os.Handler mHandler=new android.os.Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            gameTypeSpringview.onFinishFreshAndLoad();
            switch (msg.what)
            {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取分类数据",msg.obj.toString());
                    try {
                        gameTypeDataList = new Gson().fromJson(msg.obj.toString(),GameTypeDataList.class);
                        if (gameTypeDataList!=null&& gameTypeDataList.getData()!=null&& gameTypeDataList.getData().size()>0){
                            adapter.setDatas(gameTypeDataList.getData());
                            RecyclerViewHelper.initRecyclerViewV(getActivity(),gameTypeListview,true, adapter,false);
                            adapter.setOnItemClickListener(new HotGameListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemLongClick(View view, int position) {
                                    GameDeatailsActivity.actionStart(getActivity(), gameTypeDataList.getData().get(position).getId());
                                }
                            });
                            LoadingManger.getInsetance().stopFinishLoading(true);
                            error.setVisibility(View.GONE);
                        }else{
                            LoadingManger.getInsetance().stopFinishLoading("暂无相关游戏",false);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        LoadingManger.getInsetance().stopFinishLoading("数据解析异常",false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常",false);
                    Log.e("获取分类数据",msg.obj.toString());
                    break;
            }
        }
    };

    private android.os.Handler sHandler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            gameTypeSpringview.onFinishFreshAndLoad();
            switch (msg.what)
            {
                case Constant.REQUEST_SUCCESS:
                    Log.e("加载分类数据",msg.obj.toString());
                    try {
                        final GameTypeDataList list=new Gson().fromJson(msg.obj.toString(),GameTypeDataList.class);
                        if (list!=null&&list.getData()!=null&&list.getData().size()>0){
                            gameTypeDataList.getData().addAll(list.getData());
                            adapter.setDatas(gameTypeDataList.getData());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        ToastUtil.showToast("解析数据异常");
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("加载分类数据",msg.obj.toString());
                    break;
            }
        }
    };
}
