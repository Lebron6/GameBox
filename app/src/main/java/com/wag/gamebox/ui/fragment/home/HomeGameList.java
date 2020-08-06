package com.wag.gamebox.ui.fragment.home;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class HomeGameList extends BaseFragment{

private String type;
    public HomeGameList(String type) {
        this.type = type;
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_home_game;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onPause() {
        super.onPause();
    }




}
