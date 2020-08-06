package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.GetGiftResult;
import com.wag.gamebox.entity.GiftDetails;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.Utils;
import com.wag.gamebox.ui.dialog.GetGiftSuccessDialog;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by James on 2018/10/8.
 */
public class GiftDetailsActivity extends BaseActivity {

    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.layout_top)
    RelativeLayout layoutTop;
    @BindView(R.id.iv_gift_icon)
    ImageView ivGiftIcon;
    @BindView(R.id.tv_gift_name)
    TextView tvGiftName;
    @BindView(R.id.tv_gift_code)
    TextView tvGiftCode;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_gift_details)
    TextView tvGiftDetails;
    @BindView(R.id.tv_gift_time)
    TextView tvGiftTime;
    @BindView(R.id.tv_use_type)
    TextView tvUseType;
    @BindView(R.id.tv_get_gift)
    TextView tvGetGift;

    public static final String GIFT_ID = "gift_id";
    public static final String TITLE = "title";
    @BindView(R.id.layout_recever)
    RelativeLayout layoutRecever;
    private GiftDetails giftDetails;

    public static void actionStart(Context context, String title, int giftId) {
        Intent intent = new Intent(context, GiftDetailsActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(GIFT_ID, giftId);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle(getIntent().getStringExtra(TITLE));
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_gift_details;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

            getGiftInfo();


    }

    private void getGiftInfo() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getGiftDetailsUrl());

        params.addParameter("kac_id", getIntent().getIntExtra(GIFT_ID, -1)+"");
         if(PreferenceUtils.getInstance().getLoginStatus()==true){
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());}
        Log.e("details",getIntent().getIntExtra(GIFT_ID, -1)+"");
        HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
        requestUtils.doGet(params);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取礼包详情数据", msg.obj.toString());
                    try {
                        giftDetails = new Gson().fromJson(msg.obj.toString(), GiftDetails.class);
                        if (giftDetails != null && giftDetails.getData() != null) {
                            x.image().bind(ivGiftIcon, BaseUrl.getInstence().ipAddress + giftDetails.getData().getLogo_img());
                            tvGiftName.setText(giftDetails.getData().getName());
                            tvGiftDetails.setText(giftDetails.getData().getContent());
                            tvUseType.setText(giftDetails.getData().getExplain());
                            tvGiftTime.setText(giftDetails.getData().getDeadline());
                            if (giftDetails.getData().getStatus() == 1) {  //未领取
                                layoutRecever.setVisibility(View.GONE);
                                tvGetGift.setVisibility(View.VISIBLE);
                            } else {
                                tvGetGift.setVisibility(View.GONE);
                                layoutRecever.setVisibility(View.VISIBLE);
                                tvGiftCode.setText(giftDetails.getData().getCdkey());
                                tvCopy.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Utils.copy(giftDetails.getData().getCdkey());
                                    }
                                });
                            }
                        }else{
                            ToastUtil.showToast("失败？");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.showToast("数据解析异常");
                        Log.e("数据解析异常", e.toString());
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("获取礼包详情数据", msg.obj.toString());
                    break;
            }
        }
    };

    @OnClick({R.id.tv_copy, R.id.tv_get_gift})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_copy:
                Utils.copy(giftDetails.getData().getCdkey());
                break;
            case R.id.tv_get_gift:
                if (giftDetails != null && giftDetails.getData() != null) {
                    getGiftCode();
                } else {
                    ToastUtil.showToast("获取礼包信息失败");
                    return;
                }

                break;
        }
    }

    private void getGiftCode() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getReceiveGiftUrl());
        params.addParameter("kac_id", getIntent().getIntExtra(GIFT_ID, -1));
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        HttpRequestUtils requestUtils = new HttpRequestUtils(sHandler);
        requestUtils.doGet(params);
    }

    private Handler sHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("领取礼包结果", msg.obj.toString());
                    try {
                        GetGiftResult result = new Gson().fromJson(msg.obj.toString(), GetGiftResult.class);
                        if (result.getCode().equals("200")) {
                            GetGiftSuccessDialog dialog = new GetGiftSuccessDialog(GiftDetailsActivity.this, R.style.MyDialog,result.getData(), giftDetails.getData().getExplain());
                            dialog.setOnDismissListener(onDismissListener);
                            dialog.show();
                        }
                        ToastUtil.showToast(result.getMsg());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("数据解析异常", e.toString());
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("领取礼包结果", msg.obj.toString());
                    ToastUtil.showToast(msg.obj.toString());
                    break;
            }
        }
    };

    DialogInterface.OnDismissListener onDismissListener=new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            getGiftInfo();
        }
    };

}
