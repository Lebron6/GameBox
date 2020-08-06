package com.wag.gamebox.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by James on 2018/1/9.
 */

public abstract class BaseFragment extends Fragment {

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(attachLayoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        loggerSimpleName();
        return view;
    }
    public void loggerSimpleName(){
        Log.d("当前所处界面 ：",getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        initDatas();
    }

    /**
     * 注销ButterKnife
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
        Log.e(getClass().getSimpleName(),"onDestroyView");
    }

    /**
     * 绑定布局文件
     * @return 布局文件ID
     */
    protected abstract int attachLayoutRes();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 获取数据
     */
    protected abstract void initDatas();


}
