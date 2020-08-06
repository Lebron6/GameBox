package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.ui.adapter.NavPagerAdapter;
import com.wag.gamebox.ui.fragment.info.Info;
import com.wag.gamebox.ui.fragment.ranking.NewAndHotRanking;
import com.wag.gamebox.ui.view.NavitationLayout;
import com.wag.gamebox.ui.view.TitleBarManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 排行榜
 */

public class RankingActivity extends BaseActivity {


    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.nav_ranking)
    NavitationLayout navRanking;
    @BindView(R.id.vp_ranking)
    ViewPager vpRanking;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RankingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger=TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("排行榜");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_ranking;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {finish();
    }
    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        String[] strings = {"热门榜", "最新榜"};
        List<Fragment> fragments2 = new ArrayList<>();
        fragments2.add(new NewAndHotRanking("hot"));
        fragments2.add(new NewAndHotRanking("newest"));
        NavPagerAdapter viewPagerAdapter2 = new NavPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter2.setData( fragments2);
        vpRanking.setAdapter(viewPagerAdapter2);
        vpRanking.setCurrentItem(0);
        navRanking.setViewPager(this, strings, vpRanking, R.color.colorTextBlack, R.color.themeYellow, 14, 14, 0, 85,true);
        navRanking.setBgLine(this, 0, R.color.themeYellow);
        navRanking.setNavLine(this, 2, R.color.themeYellow,0);
    }
}
