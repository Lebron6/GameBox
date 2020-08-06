package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.wag.gamebox.entity.Servicer;
import com.wag.gamebox.tools.DownLoadManger;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.Utils;
import com.wag.gamebox.ui.dialog.CallServiceDialog;
import com.wag.gamebox.ui.dialog.CleanCacheDialog;
import com.wag.gamebox.ui.view.TitleBarManger;
import org.xutils.http.RequestParams;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by James on 2018/10/29.
 */
public class AboutUsActivity extends BaseActivity {

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
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_v)
    TextView tvV;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_net)
    TextView tvNet;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger=TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("关于我们");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
tvV.setText(Utils.getLocalVersionName(this)+"");
    }


    @OnClick({R.id.layout_call_sevice, R.id.layout_feed_back,R.id.layout_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_call_sevice:
                getServicerPhoneNum();
                break;
            case R.id.layout_feed_back:
            FeedBackActivity.actionStart(this);
                break;
            case R.id.layout_clear:
                CleanCacheDialog dialog=new CleanCacheDialog(this,R.style.MyDialog);
                dialog.show();

                break;
        }
    }
    private void getServicerPhoneNum() {
        RequestParams params=new RequestParams(BaseUrl.getInstence().getCallServiceUrl());
        HttpRequestUtils requestUtils=new HttpRequestUtils(cHandler);
        requestUtils.doGet(params);
    }

    private Handler cHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取客服热线", msg.obj.toString());
                    Servicer servicer=new Gson().fromJson(msg.obj.toString(),Servicer.class);
                    if (!TextUtils.isEmpty(servicer.getData())){
                        CallServiceDialog dialog=new CallServiceDialog(AboutUsActivity.this,R.style.MyDialog,servicer.getData());
                        dialog.show();
                    }else{
                        ToastUtil.showToast("获取客服热线失败");
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast("获取客服热线失败");
                    Log.e("获取客服热线失败", msg.obj.toString());
                    break;
            }
        }
    };

}
