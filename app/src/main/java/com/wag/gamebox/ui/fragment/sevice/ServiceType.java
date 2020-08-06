package com.wag.gamebox.ui.fragment.sevice;

import android.annotation.SuppressLint;
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
import com.wag.gamebox.entity.OpenService;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.activity.GameDeatailsActivity;
import com.wag.gamebox.ui.adapter.ServiceGameListAdapter;
import com.wag.gamebox.ui.fragment.BaseFragment;
import com.wag.gamebox.ui.view.LoadingManger;
import com.wag.gamebox.ui.view.SimpleFooter;
import com.wag.gamebox.ui.view.SimpleHeader;
import com.wag.gamebox.ui.view.SpringView;

import org.xutils.http.RequestParams;

import butterknife.BindView;

/**
 * Created by LeBron on 2017/2/6.
 * 开服分类
 */
@SuppressLint("ValidFragment")
public class ServiceType extends BaseFragment {

    @BindView(R.id.game_type_listview)
    RecyclerView gameTypeListview;
    @BindView(R.id.game_type_springview)
    SpringView gameTypeSpringview;
    @BindView(R.id.error)
    RelativeLayout error;
    private int type;
    private int page = 1;
    private ServiceGameListAdapter adapter;
    private OpenService openService;

    public ServiceType(int type) {
        this.type = type;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_game_type;
    }

    @Override
    protected void initViews() {
        initSpringViewStyle();
        adapter = new ServiceGameListAdapter(getActivity());
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
        getTypeGameList();
        if (adapter != null) {
            adapter.start();
            adapter.state();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        if (adapter != null) {
            adapter.delete();
        }
        super.onPause();
    }

    public void getTypeGameList() {
        LoadingManger.getInsetance().startLoading();
        RequestParams params = new RequestParams(BaseUrl.getInstence().getAppOpenSrviceUrl());
        params.addParameter("type", type);
        HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
        requestUtils.doGet(params);
    }

    private void onLoadMoreList() {
        page = ++page;
        RequestParams params = new RequestParams(BaseUrl.getInstence().getAppOpenSrviceUrl());
        params.addParameter("page", page);
        params.addParameter("type", type);
        HttpRequestUtils requestUtils = new HttpRequestUtils(sHandler);
        requestUtils.doGet(params);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            gameTypeSpringview.onFinishFreshAndLoad();
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取开服数据", msg.obj.toString());
                    try {
                        openService = new Gson().fromJson(msg.obj.toString(), OpenService.class);
                        if (openService != null && openService.getData() != null && openService.getData().size() > 0) {
                            adapter.setDatas(openService.getData());
                            RecyclerViewHelper.initRecyclerViewV(getActivity(), gameTypeListview, true, adapter);
                            adapter.setOnItemClickListener(new ServiceGameListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemLongClick(View view, int position) {
                                    GameDeatailsActivity.actionStart(getActivity(), openService.getData().get(position).getId());
                                }
                            });
                            LoadingManger.getInsetance().stopFinishLoading(true);
                            error.setVisibility(View.GONE);
                        } else {
                            LoadingManger.getInsetance().stopFinishLoading("暂无相关游戏", false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LoadingManger.getInsetance().stopFinishLoading("数据解析异常", false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
                    Log.e("获取开服数据", msg.obj.toString());
                    break;
            }
        }
    };

    private Handler sHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            gameTypeSpringview.onFinishFreshAndLoad();
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("加载开服数据", msg.obj.toString());
                    try {
                        final OpenService list = new Gson().fromJson(msg.obj.toString(), OpenService.class);
                        if (list != null && list.getData() != null && list.getData().size() > 0) {
                            openService.getData().addAll(list.getData());
                            adapter.setDatas(list.getData());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showToast("解析数据异常");
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("加载开服数据", msg.obj.toString());
                    break;
            }
        }
    };

}
