package com.wag.gamebox.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.wag.gamebox.ui.fragment.Gift;
import com.wag.gamebox.ui.fragment.Home;
import com.wag.gamebox.ui.fragment.Information;
import com.wag.gamebox.ui.fragment.Manger;
import com.wag.gamebox.ui.fragment.Mcenter;
import com.wag.gamebox.ui.fragment.My;

/**
 * Created by LeBron on 2017/1/22.
 * 主
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private Home homeFragment;
    private My myFragment;
    private Manger mangerFragment;

    public PagerAdapter(android.support.v4.app.FragmentManager fm) {
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
            if(myFragment==null){
                myFragment = new My();
                return myFragment;
            }else{
                return myFragment;
            }
        }else if(position==2){
            if(mangerFragment==null){
                mangerFragment = new Manger();
                return mangerFragment;
            }else{
                return mangerFragment;
            }
        }else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
