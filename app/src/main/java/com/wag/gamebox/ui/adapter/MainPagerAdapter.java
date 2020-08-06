package com.wag.gamebox.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import com.wag.gamebox.ui.fragment.Gift;
import com.wag.gamebox.ui.fragment.Home;
import com.wag.gamebox.ui.fragment.Information;
import com.wag.gamebox.ui.fragment.Mcenter;

/**
 * Created by LeBron on 2017/1/22.
 * 主
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private Home homeFragment;
    private Gift giftFragment;
    private Information activitysFragment;
    private Mcenter mcenterFragment;

    public MainPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            if(homeFragment==null){
                homeFragment = new Home();
                return homeFragment;
            }else{
                return homeFragment;
            }
        }else if (position==1){
            if(giftFragment==null){
                giftFragment = new Gift();
                return giftFragment;
            }else{
                return giftFragment;
            }
        }else if(position==2){
            if(activitysFragment==null){
                activitysFragment = new Information();
                return activitysFragment;
            }else{
                return activitysFragment;
            }
        }else if(position==3){
            if(mcenterFragment==null){
                mcenterFragment = new Mcenter();
                return mcenterFragment;
            }else{
                return mcenterFragment;
            }
        }else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
