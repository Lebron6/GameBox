package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.Servicer;
import com.wag.gamebox.entity.VipRight;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.wcy.htmltext.HtmlImageLoader;
import me.wcy.htmltext.HtmlText;
import me.wcy.htmltext.OnTagClickListener;

/**
 * Created by James on 2018/10/29.
 */
public class VipRightActivity extends BaseActivity {


    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.layout_back)
    LinearLayout layoutBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_sreach)
    ImageView ivSreach;
    @BindView(R.id.layout_top)
    RelativeLayout layoutTop;
    @BindView(R.id.tv_right)
    TextView tvRight;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, VipRightActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("VIP特权");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_vip_right;
    }

    @Override
    protected void initViews() {
        getVipRight();
    }

    @Override
    protected void initDatas() {

    }


    private void getVipRight() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getVIPRightUrl());
        HttpRequestUtils requestUtils = new HttpRequestUtils(cHandler);
        requestUtils.doGet(params);
    }

    private Handler cHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取会员特权", msg.obj.toString());
                    VipRight vipRight=new Gson().fromJson(msg.obj.toString(),VipRight.class);
                    if (vipRight.getData()!=null){
                        HtmlText.from(vipRight.getData())
                                .setImageLoader(new HtmlImageLoader() {
                                    @Override
                                    public void loadImage(String url, final Callback callback) {
                                        // Glide sample, you can also use other image loader

                                        Glide.with(VipRightActivity.this)
                                                .load(url)
                                                .asBitmap()
                                                .into(new SimpleTarget<Bitmap>() {

                                                    @Override
                                                    public void onResourceReady(Bitmap resource,
                                                                                GlideAnimation<? super Bitmap> glideAnimation) {
                                                        callback.onLoadComplete(resource);
                                                    }

                                                    @Override
                                                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                                        callback.onLoadFailed();
                                                    }
                                                });
                                    }

                                    @Override
                                    public Drawable getDefaultDrawable() {
                                        return ContextCompat.getDrawable(VipRightActivity.this, R.drawable.arrow);
                                    }

                                    @Override
                                    public Drawable getErrorDrawable() {
                                        return ContextCompat.getDrawable(VipRightActivity.this, R.drawable.arrow);
                                    }

                                    @Override
                                    public int getMaxWidth() {
                                        return getTextWidth();
                                    }

                                    @Override
                                    public boolean fitWidth() {
                                        return false;
                                    }
                                })
                                .setOnTagClickListener(new OnTagClickListener() {
                                    @Override
                                    public void onImageClick(Context context, List<String> imageUrlList, int position) {
                                        // image click
                                    }

                                    @Override
                                    public void onLinkClick(Context context, String url) {
                                        // link click
                                    }
                                })
                                .into(tvRight);
                    }else{
                        ToastUtil.showToast("fdf");
                    }
//                    if (!TextUtils.isEmpty(servicer.getData())) {
//

                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast("获取客服热线失败");
                    Log.e("获取客服热线失败", msg.obj.toString());
                    break;
            }
        }
    };
    private int getTextWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return dm.widthPixels - tvRight.getPaddingLeft() - tvRight.getPaddingRight();
    }

}
