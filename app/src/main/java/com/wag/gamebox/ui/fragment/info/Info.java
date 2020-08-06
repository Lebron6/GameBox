package com.wag.gamebox.ui.fragment.info;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.wag.gamebox.entity.InfoBean;
import com.wag.gamebox.entity.MoreHotGame;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.activity.GameDeatailsActivity;
import com.wag.gamebox.ui.activity.HotGameCenterActivity;
import com.wag.gamebox.ui.activity.WebViewActivity;
import com.wag.gamebox.ui.adapter.HotGameListAdapter;
import com.wag.gamebox.ui.adapter.InfomationListAdapter;
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
 * 资讯
 * Created by Administrator on 2017/2/7.
 */

@SuppressLint("ValidFragment")
public class Info extends BaseFragment {
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
    @BindView(R.id.rv_info)
    RecyclerView rvInfo;
    @BindView(R.id.information_springview)
    SpringView svInfo;
    private String type;
    private int page=1;
    private InfomationListAdapter infomationListAdapter;
    private InfoBean mInfoBean;

    @SuppressLint("ValidFragment")
    public Info(String type) {
        this.type = type;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_information_info;
    }

    @Override
    protected void initViews() {
        initSpringViewStyle();
        infomationListAdapter = new InfomationListAdapter(getActivity());
    }


    @Override
    protected void initDatas() {
        getHotGameList();
    }

    private void initSpringViewStyle() {
        LoadingManger.getInsetance().setView(error);
        svInfo.setType(SpringView.Type.FOLLOW);
        svInfo.setListener(onFreshListener);
        svInfo.setHeader(new SimpleHeader(getActivity()));
        svInfo.setFooter(new SimpleFooter(getActivity()));
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

    public void getHotGameList() {
        LoadingManger.getInsetance().startLoading();
        RequestParams params = new RequestParams(BaseUrl.getInstence().getInfoUrl());
        params.addParameter("type", type);
        HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
        requestUtils.doGet(params);
    }

    private void onLoadmoreList() {
        page = ++page;
        RequestParams params = new RequestParams(BaseUrl.getInstence().getInfoUrl());
        params.addParameter("page", page);
        params.addParameter("type", type);
        HttpRequestUtils requestUtils = new HttpRequestUtils(sHandler);
        requestUtils.doGet(params);
    }

    private android.os.Handler mHandler=new android.os.Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            svInfo.onFinishFreshAndLoad();
            switch (msg.what)
            {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取资讯更多数据",msg.obj.toString());
                    try {
                        mInfoBean = new Gson().fromJson(msg.obj.toString(),InfoBean.class);
                        if (mInfoBean !=null&& mInfoBean.getData()!=null&& mInfoBean.getData().size()>0){
                            infomationListAdapter.setDatas(mInfoBean);
                            RecyclerViewHelper.initRecyclerViewV(getActivity(), rvInfo, false, infomationListAdapter,true);
                            infomationListAdapter.setOnClickListener(new InfomationListAdapter.OnClickListener() {
                                @Override
                                public void onClickListener(View view, int position) {
                                    WebViewActivity.actionStart(getActivity(),mInfoBean.getData().get(position).getTitle(),mInfoBean.getData().get(position).getUrl());
                                }
                            });
                            LoadingManger.getInsetance().stopFinishLoading(true);
                            error.setVisibility(View.GONE);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        LoadingManger.getInsetance().stopFinishLoading("数据解析异常",false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常",false);
                    Log.e("获取资讯数据",msg.obj.toString());
                    break;
            }
        }
    };

    private android.os.Handler sHandler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            svInfo.onFinishFreshAndLoad();
            switch (msg.what)
            {
                case Constant.REQUEST_SUCCESS:
                    Log.e("加载资讯更多数据",msg.obj.toString());
                    try {
                        final InfoBean infoBean=new Gson().fromJson(msg.obj.toString(),InfoBean.class);
                        if (infoBean!=null&&infoBean.getData()!=null&&infoBean.getData().size()>0){
                            mInfoBean.getData().addAll(infoBean.getData());
                            infomationListAdapter.setDatas(mInfoBean);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        ToastUtil.showToast("解析数据异常");
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("加载资讯数据",msg.obj.toString());
                    break;
            }
        }
    };
}
