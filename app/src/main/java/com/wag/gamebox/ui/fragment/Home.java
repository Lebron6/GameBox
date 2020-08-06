package com.wag.gamebox.ui.fragment;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.callback.FlashViewListener;
import com.wag.gamebox.entity.GameInfo;
import com.wag.gamebox.entity.HomeData;
import com.wag.gamebox.tools.DownLoadManger;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.Utils;
import com.wag.gamebox.ui.activity.ClassiFicationActivity;
import com.wag.gamebox.ui.activity.DownLoadingActivity;
import com.wag.gamebox.ui.activity.GameDeatailsActivity;
import com.wag.gamebox.ui.activity.LoginActivity;
import com.wag.gamebox.ui.activity.MyGameActivity;
import com.wag.gamebox.ui.activity.QueryActivity;
import com.wag.gamebox.ui.activity.RankingActivity;
import com.wag.gamebox.ui.activity.ServiceListActivity;
import com.wag.gamebox.ui.adapter.GridGameListAdapter;
import com.wag.gamebox.ui.view.FlashView;
import com.wag.gamebox.ui.view.NotifyingScrollView;
import com.wag.gamebox.ui.view.effect.EffectConstants;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by LeBron on 2017/2/6.
 */

public class Home extends BaseFragment {


    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.flash_view)
    FlashView flashView;
    @BindView(R.id.center_my_game)
    LinearLayout centerMyGame;
    @BindView(R.id.center_classification)
    LinearLayout centerClassification;
    @BindView(R.id.center_ranking)
    LinearLayout centerRanking;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.center_service)
    LinearLayout centerService;
    @BindView(R.id.rv_hot)
    RecyclerView rvHot;
    @BindView(R.id.scrollView_home)
    NotifyingScrollView scrollViewHome;
    @BindView(R.id.my_info)
    ImageView myInfo;
    @BindView(R.id.logo1)
    RelativeLayout logo1;
    @BindView(R.id.search_text)
    TextView searchText;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.view_search)
    RelativeLayout viewSearch;
    @BindView(R.id.title_download)
    ImageView titleDownload;
    @BindView(R.id.downloading_count)
    TextView downloadingCount;
    @BindView(R.id.top_bar)
    RelativeLayout topBar;
    @BindView(R.id.title_view)
    LinearLayout titleView;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.tv_xx)
    TextView tvXx;
    private Drawable actionBarbackgroundDrawable;
    private GridGameListAdapter vipAdapter;
    private GridGameListAdapter hotAdapter;
    private GridGameListAdapter relaxationAdapter;
    private HomeData homeData;


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        vipAdapter = new GridGameListAdapter(getActivity());
        hotAdapter = new GridGameListAdapter(getActivity());
        relaxationAdapter = new GridGameListAdapter(getActivity());

        RecyclerViewHelper.initRecyclerViewG(getActivity(), rvHot, false, hotAdapter, 2);
        RecyclerViewHelper.initRecyclerViewG(getActivity(), rvHot, false, relaxationAdapter, 2);
        RecyclerViewHelper.initRecyclerViewG(getActivity(), rvHot, false, vipAdapter, 2);
//        actionBarbackgroundDrawable = getResources().getDrawable(R.color.themeYellow);
//        actionBarbackgroundDrawable.setAlpha(0);
        topBar.setBackgroundDrawable(actionBarbackgroundDrawable);
        scrollViewHome.setOnScrollChangedListener(onScrollChangedListener);
        flashView.setOnPageClickListener(new FlashViewListener() {
            @Override
            public void onClick(int position) {
                GameDeatailsActivity.actionStart(getActivity(), homeData.getData().getCarousel().get(position).getId());
            }
        });
        HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
        requestUtils.doGet(new RequestParams(BaseUrl.getInstence().getHomeDataUrl()));
    }


    private NotifyingScrollView.OnScrollChangedListener onScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {
        @Override
        public void OnScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
            final int headerHeight = topBar.getHeight();
            final float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
            final int newAlpha = (int) (ratio * 255);
//            actionBarbackgroundDrawable.setAlpha(newAlpha);
//            viewStatusBar.setAlpha(newAlpha);
        }
    };

    @Override
    protected void initDatas() {
        Log.e("是否执行onResume","~");
        if (vipAdapter != null) {
            vipAdapter.start();
            vipAdapter.state();
            vipAdapter.notifyDataSetChanged();
        }
        if (hotAdapter != null) {
            hotAdapter.start();
            hotAdapter.state();
            hotAdapter.notifyDataSetChanged();
        }
        if (relaxationAdapter != null) {
            relaxationAdapter.start();
            relaxationAdapter.state();
            relaxationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("是否执行onStart","~");

    }

    @Override
    public void onPause() {
        Log.e("是否执行onPause","~");
        if (vipAdapter != null) {
            vipAdapter.delete();
        }
        if (hotAdapter != null) {
            hotAdapter.delete();
        }
        if (relaxationAdapter != null) {
            relaxationAdapter.delete();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.e("FragmentDestory执行","~");
        super.onDestroy();

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取首页上数据", msg.obj.toString());
                    try {
                        homeData = new Gson().fromJson(msg.obj.toString(), HomeData.class);
                        if (homeData != null) {
                            if (homeData.getData().getCarousel() != null && homeData.getData().getCarousel().size() > 0) {
                                ArrayList<String> imageUrls = new ArrayList<String>();
                                for (int i = 0; i < homeData.getData().getCarousel().size(); i++) {
                                    imageUrls.add(homeData.getData().getCarousel().get(i).getMobile_carousel_img());
                                }
                                flashView.setImageUris(imageUrls);
                                flashView.setEffect(EffectConstants.DEFAULT_EFFECT);//更改图片切换的动画效果
                            }
                            updataRecyAndStatus(1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showToast("解析数据异常");
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("获取首页上数据", msg.obj.toString());
                    break;
            }
        }
    };

    @OnClick({R.id.tv_vip, R.id.tv_hot, R.id.tv_xx, R.id.center_my_game, R.id.center_classification, R.id.center_ranking, R.id.center_service, R.id.tv_other, R.id.view_search, R.id.title_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.center_my_game:
                if (PreferenceUtils.getInstance().getLoginStatus() == true) {
                    MyGameActivity.actionStart(getActivity());
                } else {
                    LoginActivity.actionStart(getActivity());
                }
                break;
            case R.id.center_classification:
                ClassiFicationActivity.actionStart(getActivity());
                break;
            case R.id.center_ranking:
                RankingActivity.actionStart(getActivity());
                break;
            case R.id.center_service:
                ServiceListActivity.actionStart(getActivity());
                break;
            case R.id.tv_other:
//                HotGameCenterActivity.actionStart(getActivity());
                ClassiFicationActivity.actionStart(getActivity());
                break;
            case R.id.view_search:
                QueryActivity.actionStart(getActivity());
                break;
            case R.id.title_download:
                DownLoadingActivity.actionStart(getActivity());
                break;
            case R.id.tv_vip:
                updataRecyAndStatus(1);
                break;
            case R.id.tv_hot:
                updataRecyAndStatus(2);
                break;
            case R.id.tv_xx:
                updataRecyAndStatus(3);
                break;
        }
    }

    private void updataRecyAndStatus(int i) {
        ViewGroup.LayoutParams layoutParams = rvHot.getLayoutParams();
        switch (i) {
            case 1:
                tvVip.setTextColor(getActivity().getResources().getColor(R.color.themeYellow));
                tvVip.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvHot.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvXx.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvHot.setTextColor(getActivity().getResources().getColor(R.color.colorTextBlack));
                tvXx.setTextColor(getActivity().getResources().getColor(R.color.colorTextBlack));
                vipAdapter.setDatas(homeData.getData().getVip());
                RecyclerViewHelper.initRecyclerViewG(getActivity(), rvHot, false, vipAdapter, 2);
                if (homeData.getData().getVip().size() % 2 == 1) {
                    layoutParams.height = Utils.dip2px(165) * ((homeData.getData().getVip().size() + 1) / 2);
                } else {
                    layoutParams.height = Utils.dip2px(165) * ((homeData.getData().getVip().size()) / 2);
                }
                rvHot.setLayoutParams(layoutParams);
                rvHot.setFocusable(false);
                vipAdapter.setOnItemClickListener(new GridGameListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemLongClick(View view, int position) {
                        GameDeatailsActivity.actionStart(getActivity(), homeData.getData().getVip().get(position).getId());
                    }
                });

                break;
            case 2:
                tvHot.setTextColor(getActivity().getResources().getColor(R.color.themeYellow));
                tvHot.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvVip.setTextColor(getActivity().getResources().getColor(R.color.colorTextBlack));
                tvXx.setTextColor(getActivity().getResources().getColor(R.color.colorTextBlack));
                tvVip.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvXx.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                hotAdapter.setDatas(homeData.getData().getHot());
                RecyclerViewHelper.initRecyclerViewG(getActivity(), rvHot, false, hotAdapter, 2);

                if (homeData.getData().getVip().size() % 2 == 1) {
                    layoutParams.height = Utils.dip2px(165) * ((homeData.getData().getHot().size() + 1) / 2);
                } else {
                    layoutParams.height = Utils.dip2px(165) * ((homeData.getData().getHot().size()) / 2);
                }
                rvHot.setLayoutParams(layoutParams);
                rvHot.setFocusable(false);
                hotAdapter.setOnItemClickListener(new GridGameListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemLongClick(View view, int position) {
                        GameDeatailsActivity.actionStart(getActivity(), homeData.getData().getHot().get(position).getId());
                    }
                });

                break;
            case 3:
                tvXx.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvXx.setTextColor(getActivity().getResources().getColor(R.color.themeYellow));
                tvHot.setTextColor(getActivity().getResources().getColor(R.color.colorTextBlack));
                tvHot.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvVip.setTextColor(getActivity().getResources().getColor(R.color.colorTextBlack));
                tvVip.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                relaxationAdapter.setDatas(homeData.getData().getRelaxation());
                RecyclerViewHelper.initRecyclerViewG(getActivity(), rvHot, false, relaxationAdapter, 2);
                if (homeData.getData().getVip().size() % 2 == 1) {
                    layoutParams.height = Utils.dip2px(165) * ((homeData.getData().getRelaxation().size() + 1) / 2);
                } else {
                    layoutParams.height = Utils.dip2px(165) * ((homeData.getData().getRelaxation().size()) / 2);
                }
                rvHot.setFocusable(false);
                relaxationAdapter.setOnItemClickListener(new GridGameListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemLongClick(View view, int position) {
                        GameDeatailsActivity.actionStart(getActivity(), homeData.getData().getRelaxation().get(position).getId());
                    }
                });


                break;
        }
    }

}
