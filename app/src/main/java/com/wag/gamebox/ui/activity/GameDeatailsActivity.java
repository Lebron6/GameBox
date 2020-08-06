package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tencent.tauth.Tencent;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.callback.CommentCallListener;
import com.wag.gamebox.callback.DownloadObserver;
import com.wag.gamebox.entity.CallBackResult;
import com.wag.gamebox.entity.GameDetails;
import com.wag.gamebox.entity.GameInfo;
import com.wag.gamebox.tools.DbUtils;
import com.wag.gamebox.tools.DownLoadManger;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.adapter.NavPagerAdapter;
import com.wag.gamebox.ui.fragment.gdetails.Comment;
import com.wag.gamebox.ui.fragment.gdetails.Gift;
import com.wag.gamebox.ui.fragment.gdetails.ScreenShot;
import com.wag.gamebox.ui.dialog.InputTextMsgDialog;
import com.wag.gamebox.ui.view.LoadingManger;
import com.wag.gamebox.ui.view.NavitationLayout;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by James on 2018/9/28.
 */
public class GameDeatailsActivity extends BaseActivity implements DownloadObserver {

    @BindView(R.id.layout_top)
    RelativeLayout layoutTop;
    @BindView(R.id.res)
    LinearLayout res;
    @BindView(R.id.loading)
    ImageView loading;
    @BindView(R.id.layout_loading)
    LinearLayout layoutLoading;
    @BindView(R.id.error)
    RelativeLayout error;
    @BindView(R.id.layout_error)
    LinearLayout layoutError;
    @BindView(R.id.iv_game_icon)
    ImageView ivGameIcon;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.tv_game_name)
    TextView tvGameName;
    @BindView(R.id.tv_game_type)
    TextView tvGameType;
    @BindView(R.id.tv_game_size)
    TextView tvGameSize;
    @BindView(R.id.tv_nomal_v)
    TextView tvNomalV;
    @BindView(R.id.tv_game_version)
    TextView tvGameVersion;
    @BindView(R.id.nav_game_details)
    NavitationLayout navGameDetails;
    @BindView(R.id.vp_game_details)
    ViewPager vpGameDetails;
    @BindView(R.id.tv_download)
    TextView tvDownload;

    public static final String GAME_ID = "game_id";
    @BindView(R.id.tv_to_comment)
    TextView tvToComment;
    private GameDetails gameDetails;
    private DbManager db;
    private Tencent mTencent;
    private Comment comment;

    public static void actionStart(Context context, int gameId) {
        Intent intent = new Intent(context, GameDeatailsActivity.class);
        intent.putExtra(GAME_ID, gameId);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("游戏详情");
        manger.setBack();
        manger.setShare();
        DownLoadManger.getInstance().addObserver(this);
        if (gameDetails != null && gameDetails.getData() != null && gameDetails.getData().getGame_info() != null) {
            DownLoadManger.getInstance().isInstall(gameDetails.getData().getGame_info());
        }
    }

    @Override
    public void onPause() {
        DownLoadManger.getInstance().deleteObserver(this);
        super.onPause();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_game_details;
    }

    @Override
    protected void initViews() {
        db = DbUtils.getDB();
    }

    @Override
    protected void initDatas() {
        LoadingManger loadingManger = LoadingManger.getInsetance();
        loadingManger.setView(error);
        RequestParams params = new RequestParams(BaseUrl.getInstence().getGameDetailsUrl());
        params.addParameter("id", getIntent().getIntExtra(GAME_ID, -1));
        HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
        requestUtils.doGet(params);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取游戏详情数据", msg.obj.toString());
                    try {
                        gameDetails = new Gson().fromJson(msg.obj.toString(), GameDetails.class);
                        if (gameDetails != null && gameDetails.getData() != null) {
                            if (gameDetails.getData().getGame_info() != null) {
                                x.image().bind(ivGameIcon, BaseUrl.getInstence().ipAddress + gameDetails.getData().getGame_info().getLogo_img());
                                tvGameName.setText(gameDetails.getData().getGame_info().getGame_name());
                                tvGameSize.setText(gameDetails.getData().getGame_info().getGame_size());
                                tvGameType.setText(gameDetails.getData().getGame_info().getType_name());
                                tvGameVersion.setText(gameDetails.getData().getGame_info().getDown_count()); //修改为游戏版本
                                DownLoadManger.getInstance().isInstall(gameDetails.getData().getGame_info());
                                GameInfo byId = null;
                                try {
                                    byId = db.findById(GameInfo.class, gameDetails.getData().getGame_info().getId());
                                    if (byId != null) {
                                        refalsh(byId);
                                    }
                                } catch (DbException e) {
                                    e.printStackTrace();
                                    Log.e("Handler里的数据库", e.toString());
                                }
                            }
                            if (gameDetails.getData().getGame_details() != null || gameDetails.getData().getKac_list() != null||gameDetails.getData().getComments()!=null) {
                                attachFragments(gameDetails);//加载详情页两个Fragment
                            }
                            LoadingManger.getInsetance().stopFinishLoading(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LoadingManger.getInsetance().stopFinishLoading("数据解析异常", false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
                    Log.e("获取游戏详情数据", msg.obj.toString());
                    break;
            }
        }
    };

    private void attachFragments(GameDetails gameDetails) {

        String[] strings = {"介绍", "礼包", "评论"};
        List<Fragment> fragments2 = new ArrayList<>();
        fragments2.add(new ScreenShot(gameDetails.getData().getGame_details()));
        fragments2.add(new Gift());
        comment=new Comment();
        fragments2.add(comment);
        NavPagerAdapter viewPagerAdapter2 = new NavPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter2.setData(fragments2);
        vpGameDetails.setAdapter(viewPagerAdapter2);
        vpGameDetails.setCurrentItem(0);
//        vpGameDetails.setOffscreenPageLimit(2);
        navGameDetails.setViewPager(this,tvToComment, strings, vpGameDetails, R.color.themeGrayText, R.color.themeYellow, 14, 16, 0, 85, true);
        navGameDetails.setBgLine(this, 0, R.color.themeYellow);
        navGameDetails.setNavLine(this, 2, R.color.themeYellow, 0);
    }

    @OnClick({R.id.iv_share, R.id.tv_download,R.id.tv_to_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_download:
                downLoad(verifLocalIsHave());
                break;
            case R.id.tv_to_comment:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(this);
                } else {
                    InputTextMsgDialog  inputTextMsgDialog = new InputTextMsgDialog(GameDeatailsActivity.this, R.style.MyDialog);
                    inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                        @Override
                        public void onTextSend(String msg) {
                            //点击发送按钮后，回调此方法，msg为输入的值
                            send(msg);
                        }
                    });
                    inputTextMsgDialog.show();
                }
                break;
            case R.id.iv_share:
                GameDetailsShareActivity.actionStart(GameDeatailsActivity.this, BaseUrl.getInstence().ipAddress + gameDetails.getData().getGame_info().getAndroid(), gameDetails.getData().getGame_info().getGame_name(), gameDetails.getData().getGame_details().getGame_details(), BaseUrl.getInstence().ipAddress + gameDetails.getData().getGame_info().getLogo_img());
                break;
        }
    }

    private void send(String msg) {
        if (TextUtils.isEmpty(msg)){
            ToastUtil.showToast("请输入评论内容");
            return;
        }else{
            RequestParams params = new RequestParams(BaseUrl.getInstence().getCommentUrl());
            params.addParameter("game_id", getIntent().getIntExtra(GAME_ID, -1));
            params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
            params.addParameter("content", msg);
            Log.e("评论",params.toString());
            HttpRequestUtils requestUtils = new HttpRequestUtils(cHandler);
            requestUtils.doGet(params);
        }
    }


    private Handler cHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("评论", msg.obj.toString());
                    try {
                        CallBackResult result = new Gson().fromJson(msg.obj.toString(), CallBackResult.class);
                        if (result.getCode().equals("200")) {
                            ToastUtil.showToast("评论成功");
                            comment.sendMoment(1 );
                        } else {
                            ToastUtil.showToast(result.getMsg());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showToast("评论异常");
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("评论失败数据", msg.obj.toString());
                    ToastUtil.showToast("网络连接异常");
                    break;
            }
        }
    };

    private void downLoad(GameInfo gameInfo) {
        switch (gameInfo.getBtn_tatus()) {
            case DownLoadManger.DOWNLOAD_NOT:
                startDownLoad(gameInfo);
                break;
            case DownLoadManger.DOWNLOAD_LOADING:
                puseDownLoad(gameInfo);
                break;
            case DownLoadManger.DOWNLOAD_SUCCESS:
                inStall(gameInfo);
                break;
            case DownLoadManger.DOWNLOAD_FAILED:
                startDownLoad(gameInfo);
                break;
            case DownLoadManger.DOWNLOAD_INSTALL:
                open(gameInfo);
                break;
            case DownLoadManger.DOWNLOAD_PAUSE:
                startDownLoad(gameInfo);
                break;
        }
    }

    public void startDownLoad(GameInfo app) {
        DownLoadManger.getInstance().down(app);
    }

    public void puseDownLoad(GameInfo app) {
        DownLoadManger.getInstance().pause(app);
    }

    public void inStall(GameInfo app) {
        DownLoadManger.getInstance().install(app);
    }

    public void open(GameInfo app) {
        DownLoadManger.getInstance().open(app);
    }

    /**
     * 返回继续或新的下载任务
     */
    public GameInfo verifLocalIsHave() {
        try {
            GameInfo byId = db.findById(GameInfo.class, getIntent().getIntExtra(GAME_ID, -1));
            if (byId == null) {             //若本地没有下载记录，重新下载该游戏，存入数据库  //缺少包名字段
                return gameDetails.getData().getGame_info();
            } else {
                return byId;
            }
        } catch (DbException e) {
            e.printStackTrace();
            Log.e("查询数据库GameInfo异常", e.toString());
            return null;
        }
    }

    @Override
    public void onDownloadStateChanged(DownLoadManger manager, GameInfo info) {
        try {
            if (info.getId() == getIntent().getIntExtra(GAME_ID, -1)) {
                Log.e("是否执行",getIntent().getIntExtra(GAME_ID, -1)+"");
                refalsh(info);
            }
        } catch (Exception e) {
            Log.e("游戏详情的观察者出错了~", e.toString());
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
                tvDownload.setText("等待...");
                break;
            case DownLoadManger.DOWNLOAD_NOT:
                tvDownload.setText("下载");
                break;
            case DownLoadManger.DOWNLOAD_LOADING:
                int progress = (int) (info.getProgress() * 100f / info.getZsize() + 0.5f);
                tvDownload.setText(progress + "%");
                tvDownload.setEnabled(true);
                break;
            case DownLoadManger.DOWNLOAD_SUCCESS:
                tvDownload.setText("安装");
                tvDownload.setEnabled(true);
                break;
            case DownLoadManger.DOWNLOAD_FAILED:
                tvDownload.setText("重试");
                tvDownload.setEnabled(true);
                break;
            case DownLoadManger.DOWNLOAD_INSTALL:
                tvDownload.setText("打开");
                tvDownload.setEnabled(true);
                break;
            case DownLoadManger.DOWNLOAD_PAUSE:
                tvDownload.setText("继续");
                tvDownload.setEnabled(true);
                break;
        }
    }

}
