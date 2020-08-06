package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.ui.adapter.NavPagerAdapter;
import com.wag.gamebox.ui.fragment.sevice.ServiceType;
import com.wag.gamebox.ui.view.NavitationLayout;
import com.wag.gamebox.ui.view.TitleBarManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by James on 2018/10/8.
 */
public class ServiceListActivity extends BaseActivity {

    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.layout_top)
    RelativeLayout layoutTop;
    @BindView(R.id.nav_sevice)
    NavitationLayout navSevice;
    @BindView(R.id.vp_sevice)
    ViewPager vpSevice;

    @Override
    protected void initTitle() {
        TitleBarManger manger=TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("开服表");
        manger.setBack();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ServiceListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_service;
    }

    @Override
    protected void initViews() {
        String[] strings = {"历史开服", "今日开服","未来开服"};
        List<Fragment> fragments2 = new ArrayList<>();
        fragments2.add(new ServiceType(1));
        fragments2.add(new ServiceType(2));
        fragments2.add(new ServiceType(3));
        NavPagerAdapter viewPagerAdapter2 = new NavPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter2.setData( fragments2);
        vpSevice.setAdapter(viewPagerAdapter2);
        vpSevice.setCurrentItem(0);
        navSevice.setViewPager(this, strings, vpSevice, R.color.colorTextBlack, R.color.themeYellow, 14, 14, 0, 55,true);
        navSevice.setBgLine(this, 0, R.color.themeYellow);
        navSevice.setNavLine(this, 2, R.color.themeYellow,0);
    }

    @Override
    protected void initDatas() {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {finish();
    }
}
