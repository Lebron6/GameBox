package com.wag.gamebox.ui.fragment.gdetails;

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
import com.wag.gamebox.callback.OnGiftGetSucCallBack;
import com.wag.gamebox.entity.AllGiftsOfGame;
import com.wag.gamebox.entity.GameDetails;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.ui.activity.GiftDetailsActivity;
import com.wag.gamebox.ui.adapter.AllGiftOfGameAdapter;
import com.wag.gamebox.ui.fragment.BaseFragment;
import com.wag.gamebox.ui.view.LoadingManger;

import org.xutils.http.RequestParams;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.wag.gamebox.ui.activity.GameDeatailsActivity.GAME_ID;

/**
 * Created by James on 2018/9/29.
 */
@SuppressLint("ValidFragment")
public class Gift extends BaseFragment implements OnGiftGetSucCallBack {

    @BindView(R.id.rv_gift)
    RecyclerView rvGift;
    List<GameDetails.DataBean.KacListBean> kac_list;
    @BindView(R.id.error)
    RelativeLayout error;
    private AllGiftsOfGame allGiftsOfGame;
    private AllGiftOfGameAdapter adapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_gamedetails_gift;
    }

    @Override
    protected void initViews() {
        adapter = new AllGiftOfGameAdapter(getActivity());
        adapter.setGetGiftSucCallBack(this);
        LoadingManger.getInsetance().setView(error);
    }

    @Override
    protected void initDatas() {
        LoadingManger.getInsetance().startLoading();
        getAllGiftByGame();
    }

    AllGiftOfGameAdapter.OnItemClickListener onItemClickListener = new AllGiftOfGameAdapter.OnItemClickListener() {
        @Override
        public void onItemLongClick(View view, int position) {
            Log.e("传过去的Id", allGiftsOfGame.getData().getKac_list().get(position).getId() + "");
            GiftDetailsActivity.actionStart(getActivity(), allGiftsOfGame.getData().getKac_list().get(position).getName(), allGiftsOfGame.getData().getKac_list().get(position).getId());
        }
    };

    private void getAllGiftByGame() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getAllGiftsOfGameUrl());
        params.addParameter("id", getActivity().getIntent().getIntExtra(GAME_ID, -1));
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
                    Log.e("获取游戏所有礼包数据", msg.obj.toString());
                    try {
                        allGiftsOfGame = new Gson().fromJson(msg.obj.toString(), AllGiftsOfGame.class);
                        if (allGiftsOfGame != null && allGiftsOfGame.getData() != null) {
                            if (allGiftsOfGame.getData().getKac_list() != null && allGiftsOfGame.getData().getKac_list().size() > 0) {
                                adapter.setDatas(allGiftsOfGame);
                                adapter.setOnItemClickListener(onItemClickListener);
                                RecyclerViewHelper.initRecyclerViewV(getActivity(), rvGift, false, adapter);
                                LoadingManger.getInsetance().stopFinishLoading(true);
                                error.setVisibility(View.GONE);
                            }else{
                                LoadingManger.getInsetance().stopFinishLoading("暂无相关礼包",false);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LoadingManger.getInsetance().stopFinishLoading("数据解析异常",false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("获取游戏所有礼包数据", msg.obj.toString());
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常",false);
                    break;
            }
        }
    };

    @Override
    public void onGiftGetSucCallBack() {
        getAllGiftByGame();
    }

}
