package com.wag.gamebox.ui.fragment.ranking;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.callback.DownloadObserver;
import com.wag.gamebox.entity.GameDetails;
import com.wag.gamebox.entity.GameInfo;
import com.wag.gamebox.entity.Ranking;
import com.wag.gamebox.tools.DbUtils;
import com.wag.gamebox.tools.DownLoadManger;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.adapter.RankingGameListAdapter;
import com.wag.gamebox.ui.fragment.BaseFragment;
import com.wag.gamebox.ui.view.FilletImageView;
import com.wag.gamebox.ui.view.LoadingManger;
import com.wag.gamebox.ui.view.SimpleFooter;
import com.wag.gamebox.ui.view.SimpleHeader;
import com.wag.gamebox.ui.view.SpringView;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class NewAndHotRanking extends BaseFragment implements DownloadObserver {

    @BindView(R.id.sv_ranking)
    SpringView svRanking;
    Unbinder unbinder;
    @BindView(R.id.linearLayout_Top2)
    LinearLayout linearLayoutTop2;
    @BindView(R.id.linearLayout_Top3)
    LinearLayout linearLayoutTop3;
    @BindView(R.id.relativeLayout_Ranking)
    LinearLayout relativeLayoutRanking;
    @BindView(R.id.ranking_line)
    View rankingLine;
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
    private String type;
    private Ranking mRanking;
    private RankingGameListAdapter adapter;

    public NewAndHotRanking(String type) {
        this.type = type;
    }

    @BindView(R.id.ranking_img_top2)
    FilletImageView rankingImgTop2;
    @BindView(R.id.ranking_top2_game_name)
    TextView rankingTop2GameName;
    @BindView(R.id.tv_down_load_one)
    TextView tvDownLoadOne;
    @BindView(R.id.ranking_img_top1)
    FilletImageView rankingImgTop1;
    @BindView(R.id.ranking_top1_game_name)
    TextView rankingTop1GameName;
    @BindView(R.id.tv_down_load_two)
    TextView tvDownLoadTwo;
    @BindView(R.id.ranking_img_top3)
    FilletImageView rankingImgTop3;
    @BindView(R.id.ranking_top3_game_name)
    TextView rankingTop3GameName;
    @BindView(R.id.tv_down_load_three)
    TextView tvDownLoadThree;
    @BindView(R.id.rv_ranking)
    RecyclerView rvRanking;
    @BindView(R.id.linearLayout_Top1)
    LinearLayout linearLayoutTop1;
    private int page = 1;
    private List<GameInfo> otherGames;
    private DbManager db;
    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_ranking_new_and_hot;
    }

    @Override
    protected void initViews() {
        adapter = new RankingGameListAdapter(getActivity());
        db = DbUtils.getDB();
        initSpringViewStyle();
        LoadingManger.getInsetance().setView(error);
        LoadingManger.getInsetance().startLoading();
        getHotGameList();
    }

    @Override
    protected void initDatas() {
        if (adapter != null) {
            adapter.start();
            adapter.state();
            adapter.notifyDataSetChanged();
        }
        DownLoadManger.getInstance().addObserver(this);
        if (mRanking!=null&&mRanking.getData()!=null&&mRanking.getData().get(0) != null) {
            DownLoadManger.getInstance().isInstall(mRanking.getData().get(0) );
        }
        if (mRanking!=null&&mRanking.getData()!=null&&mRanking.getData().get(1)  != null) {
            DownLoadManger.getInstance().isInstall(mRanking.getData().get(1) );
        }
        if (mRanking!=null&&mRanking.getData()!=null&&mRanking.getData().get(2)  != null) {
            DownLoadManger.getInstance().isInstall(mRanking.getData().get(2) );
        }
    }

    @Override
    public void onPause() {
        if (adapter != null) {
            adapter.delete();
        }
        DownLoadManger.getInstance().deleteObserver(this);
        super.onPause();
    }

    /**
     * 观察者模式通知下载状态发生改变
     *
     * @param manager
     * @param info
     */
    @Override
    public void onDownloadStateChanged(DownLoadManger manager, GameInfo info) {
        try {
            if (info.getId() == mRanking.getData().get(0).getId() || info.getId() == mRanking.getData().get(1).getId() || info.getId() == mRanking.getData().get(2).getId() ) {
                refalsh(info);
            }
        } catch (Exception e) {
            Log.e("排行榜的观察者出错了~", e.toString());
        }
    }

    /**
     * 刷新下载状态
     *
     * @param info
     */
    private void refalsh(GameInfo info) {
        switch (info.getBtn_tatus()) {
            case DownLoadManger.DOWNLOAD_WAITING:
                if (info.getId() == mRanking.getData().get(0).getId()) {
                    tvDownLoadOne.setText("等待...");
                } else if (info.getId() == mRanking.getData().get(1).getId()) {
                    tvDownLoadTwo.setText("等待...");
                } else if (info.getId() == mRanking.getData().get(2).getId()) {
                    tvDownLoadThree.setText("等待...");
                }
                break;
            case DownLoadManger.DOWNLOAD_NOT:
                if (info.getId() == mRanking.getData().get(0).getId()) {
                    tvDownLoadOne.setText("下载");
                } else if (info.getId() == mRanking.getData().get(1).getId()) {
                    tvDownLoadTwo.setText("下载");
                } else if (info.getId() == mRanking.getData().get(2).getId()) {
                    tvDownLoadThree.setText("下载");
                }
                break;
            case DownLoadManger.DOWNLOAD_LOADING:
                int progress = (int) (info.getProgress() * 100f / info.getZsize() + 0.5f);
                if (info.getId() == mRanking.getData().get(0).getId()) {
                    tvDownLoadOne.setText(progress + "%");
                    tvDownLoadOne.setEnabled(true);
                } else if (info.getId() == mRanking.getData().get(1).getId()) {
                    tvDownLoadTwo.setText(progress + "%");
                    tvDownLoadTwo.setEnabled(true);
                } else if (info.getId() == mRanking.getData().get(2).getId()) {
                    tvDownLoadThree.setText(progress + "%");
                    tvDownLoadThree.setEnabled(true);
                }
                break;
            case DownLoadManger.DOWNLOAD_SUCCESS:
                if (info.getId() == mRanking.getData().get(0).getId()) {
                    tvDownLoadOne.setText("安装");
                    tvDownLoadOne.setEnabled(true);
                } else if (info.getId() == mRanking.getData().get(1).getId()) {
                    tvDownLoadTwo.setText("安装");
                    tvDownLoadTwo.setEnabled(true);
                } else if (info.getId() == mRanking.getData().get(2).getId()) {
                    tvDownLoadThree.setText("安装");
                    tvDownLoadThree.setEnabled(true);
                }
                break;
            case DownLoadManger.DOWNLOAD_FAILED:
                if (info.getId() == mRanking.getData().get(0).getId()) {
                    tvDownLoadOne.setText("重试");
                    tvDownLoadOne.setEnabled(true);
                } else if (info.getId() == mRanking.getData().get(1).getId()) {
                    tvDownLoadTwo.setText("重试");
                    tvDownLoadTwo.setEnabled(true);
                } else if (info.getId() == mRanking.getData().get(2).getId()) {
                    tvDownLoadThree.setText("重试");
                    tvDownLoadThree.setEnabled(true);
                }
                break;
            case DownLoadManger.DOWNLOAD_INSTALL:
                if (info.getId() == mRanking.getData().get(0).getId()) {
                    tvDownLoadOne.setText("打开");
                    tvDownLoadOne.setEnabled(true);
                } else if (info.getId() == mRanking.getData().get(1).getId()) {
                    tvDownLoadTwo.setText("打开");
                    tvDownLoadTwo.setEnabled(true);
                } else if (info.getId() == mRanking.getData().get(2).getId()) {
                    tvDownLoadThree.setText("打开");
                    tvDownLoadThree.setEnabled(true);
                }
                break;
            case DownLoadManger.DOWNLOAD_PAUSE:
                if (info.getId() == mRanking.getData().get(0).getId()) {
                    tvDownLoadOne.setText("继续");
                    tvDownLoadOne.setEnabled(true);
                } else if (info.getId() == mRanking.getData().get(1).getId()) {
                    tvDownLoadTwo.setText("继续");
                    tvDownLoadTwo.setEnabled(true);
                } else if (info.getId() == mRanking.getData().get(2).getId()) {
                    tvDownLoadThree.setText("继续");
                    tvDownLoadThree.setEnabled(true);
                }
                break;
        }
    }

    private void initSpringViewStyle() {
        svRanking.setType(SpringView.Type.FOLLOW);
        svRanking.setListener(onFreshListener);
        svRanking.setHeader(new SimpleHeader(getActivity()));
        svRanking.setFooter(new SimpleFooter(getActivity()));
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
        HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
        RequestParams params = new RequestParams(BaseUrl.getInstence().getGameRankingUrl());
        params.addParameter("type", type);
        requestUtils.doGet(params);
    }

    private void onLoadmoreList() {
        page = ++page;
        RequestParams params = new RequestParams(BaseUrl.getInstence().getGameRankingUrl());
        params.addParameter("page", page);
        params.addParameter("type", type);
        HttpRequestUtils requestUtils = new HttpRequestUtils(sHandler);
        requestUtils.doGet(params);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            svRanking.onFinishFreshAndLoad();
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取排行榜数据", msg.obj.toString());
                    mRanking = new Gson().fromJson(msg.obj.toString(), Ranking.class);
                    if (mRanking != null && mRanking.getData() != null && mRanking.getData().size() > 0) {
                        error.setVisibility(View.GONE);
                        LoadingManger.getInsetance().stopFinishLoading(true);
                        if (mRanking.getData().get(0) != null) {
                            x.image().bind(rankingImgTop1, BaseUrl.getInstence().ipAddress+mRanking.getData().get(0).getLogo_img());
                            rankingTop1GameName.setText(mRanking.getData().get(0).getGame_name());
                            DownLoadManger.getInstance().isInstall(mRanking.getData().get(0));
                            try {
                                if ( db.findById(GameInfo.class, mRanking.getData().get(0).getId())!=null){
                                    refalsh( mRanking.getData().get(0));
                                }
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        } else {
                            linearLayoutTop1.setVisibility(View.INVISIBLE);
                        }
                        if (mRanking.getData().get(1) != null) {
                            x.image().bind(rankingImgTop2, BaseUrl.getInstence().ipAddress+mRanking.getData().get(1).getLogo_img());
                            rankingTop2GameName.setText(mRanking.getData().get(1).getGame_name());
                            DownLoadManger.getInstance().isInstall(mRanking.getData().get(1));
                            try {
                                if ( db.findById(GameInfo.class, mRanking.getData().get(1).getId())!=null){
                                    refalsh( mRanking.getData().get(1));
                                }
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        } else {
                            linearLayoutTop2.setVisibility(View.INVISIBLE);
                        }
                        if (mRanking.getData().get(2) != null) {
                            x.image().bind(rankingImgTop3, BaseUrl.getInstence().ipAddress+mRanking.getData().get(2).getLogo_img());
                            rankingTop3GameName.setText(mRanking.getData().get(2).getGame_name());
                            DownLoadManger.getInstance().isInstall(mRanking.getData().get(2));
                            try {
                                if ( db.findById(GameInfo.class, mRanking.getData().get(2).getId())!=null){
                                    refalsh( mRanking.getData().get(2));
                                }
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        } else {
                            linearLayoutTop3.setVisibility(View.INVISIBLE);
                        }
                        if (mRanking.getData().size()>3){
                            otherGames=mRanking.getData().subList(3,mRanking.getData().size());
                            adapter.setDatas(otherGames);
                            RecyclerViewHelper.initRecyclerViewV(getActivity(), rvRanking, true, adapter);
                        }
                    } else {
                        LoadingManger.getInsetance().stopFinishLoading("暂无排行信息", false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
                    Log.e("获取排行榜数据", msg.obj.toString());
                    break;
            }
        }
    };

    private Handler sHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            svRanking.onFinishFreshAndLoad();
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("加载排行榜数据", msg.obj.toString());
                   Ranking ranking = new Gson().fromJson(msg.obj.toString(), Ranking.class);
                    if (ranking != null && ranking.getData().size() > 0) {
                        otherGames.addAll(ranking.getData());
                        adapter.setDatas(otherGames);
                        RecyclerViewHelper.initRecyclerViewV(getActivity(), rvRanking, true, adapter);
                    } else {
                        ToastUtil.showToast("已加载完相关游戏~");
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("加载排行榜数据", msg.obj.toString());
                    break;
            }
        }
    };


    @OnClick({R.id.tv_down_load_one, R.id.tv_down_load_two, R.id.tv_down_load_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_down_load_one:
                verLocalIsHave(mRanking.getData().get(0));
                break;
            case R.id.tv_down_load_two:
                verLocalIsHave(mRanking.getData().get(1));
                break;
            case R.id.tv_down_load_three:
                verLocalIsHave(mRanking.getData().get(2));
                break;
        }
    }

    private void verLocalIsHave(GameInfo gameInfo){
        try {
            GameInfo byId = db.findById(GameInfo.class, gameInfo.getId());    //根据游戏id查询本地是否有下载记录
            if (byId != null) {
                toDownLoad(byId);       //若有下载记录，从本地继续下载
            } else {
                toDownLoad(gameInfo);   //若无下载记录，从新下载
            }
        } catch (DbException e) {
            e.printStackTrace();
            Log.e("数据库异常", e.toString());
        }
    }

    public void toDownLoad(GameInfo info) {
        switch (info.getBtn_tatus()) {
            case DownLoadManger.DOWNLOAD_NOT:
                StartDownLoad(info);
                break;
            case DownLoadManger.DOWNLOAD_LOADING:
                PuseDownLoad(info);
                break;
            case DownLoadManger.DOWNLOAD_SUCCESS:
                InStall(info);
                break;
            case DownLoadManger.DOWNLOAD_FAILED:
                StartDownLoad(info);
                break;
            case DownLoadManger.DOWNLOAD_INSTALL:
                Open(info);
                break;
            case DownLoadManger.DOWNLOAD_PAUSE:
                StartDownLoad(info);
                break;
        }
    }

    public void StartDownLoad(GameInfo app) {
        DownLoadManger.getInstance().down(app);
    }

    public void PuseDownLoad(GameInfo app) {
        DownLoadManger.getInstance().pause(app);
    }

    public void InStall(GameInfo app) {
        DownLoadManger.getInstance().install(app);
    }

    public void Open(GameInfo app) {
        DownLoadManger.getInstance().open(app);
    }


}
