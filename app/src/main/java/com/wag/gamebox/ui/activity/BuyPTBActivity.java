package com.wag.gamebox.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.AliPayEntity;
import com.wag.gamebox.entity.AuthResult;
import com.wag.gamebox.entity.PayResult;
import com.wag.gamebox.entity.VipOpen;
import com.wag.gamebox.entity.WXPayEntity;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.view.TitleBarManger;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.xutils.http.RequestParams;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wag.gamebox.api.Constant.REQUEST_FAIL;
import static com.wag.gamebox.api.Constant.REQUEST_SUCCESS;

/**
 * Created by James on 2018/10/9.
 */
public class BuyPTBActivity extends BaseActivity {

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
    @BindView(R.id.rb_one)
    CheckBox rbOne;
    @BindView(R.id.rb_two)
    CheckBox rbTwo;
    @BindView(R.id.rb_three)
    CheckBox rbThree;
    @BindView(R.id.rb_four)
    CheckBox rbFour;
    @BindView(R.id.rb_five)
    CheckBox rbFive;
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.txt_y)
    TextView txtY;
    @BindView(R.id.tv_ptb_num)
    TextView tvPtbNum;
    @BindView(R.id.txt_pay)
    TextView txtPay;
    @BindView(R.id.img_alipay)
    ImageView imgAlipay;
    @BindView(R.id.cb_alipay)
    CheckBox cbAliPay;
    @BindView(R.id.img_wechat_pay)
    ImageView imgWechatPay;
    @BindView(R.id.cb_wechat_pay)
    CheckBox cbWechatPay;
    @BindView(R.id.tv_buy_ptb)
    TextView tvBuyPtb;
    @BindView(R.id.rg_num)
    LinearLayout rgNum;
    @BindView(R.id.tv_vip_level)
    TextView tvVipLevel;
    @BindView(R.id.txt_discount)
    TextView txtDiscount;
    private VipOpen vipOpen;
    private String ptbNum;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, BuyPTBActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("购买平台币");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_buy_ptb;
    }

    @Override
    protected void initViews() {
        etInputNum.addTextChangedListener(watcher);
        rbOne.setOnCheckedChangeListener(onCheckedChangeListener);
        rbTwo.setOnCheckedChangeListener(onCheckedChangeListener);
        rbThree.setOnCheckedChangeListener(onCheckedChangeListener);
        rbFour.setOnCheckedChangeListener(onCheckedChangeListener);
        rbFive.setOnCheckedChangeListener(onCheckedChangeListener);
        cbWechatPay.setOnCheckedChangeListener(onPayTypeSelectChangeListener);
        cbAliPay.setOnCheckedChangeListener(onPayTypeSelectChangeListener);
        etInputNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etInputNum.setBackgroundResource(R.drawable.radio_bg_ptb_num_pre);
            }
        });
        getDiscountOfVip();
    }

    private void getDiscountOfVip() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getVIPDiscountUrl());
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        HttpRequestUtils requestUtils = new HttpRequestUtils(dHandler);
        requestUtils.doGet(params);
    }

    private Handler dHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取会员充值折扣", msg.obj.toString());
                    vipOpen = new Gson().fromJson(msg.obj.toString(), VipOpen.class);
                    if (vipOpen.getCode().equals("200")) {
                        if (vipOpen.getData() != null) {
                            tvVipLevel.setText(vipOpen.getData().getVip());
                            if (vipOpen.getData().getOpen_vip().equals("1")){
                                if (vipOpen.getData().getDiscount().equals("0")){
                                    txtDiscount.setText("("+"暂无折扣"+")");
                                }else{
                                    txtDiscount.setText("("+vipOpen.getData().getDiscount()+"折)");
                                }
                            }else{
                                txtDiscount.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            ToastUtil.showToast(vipOpen.getMsg());
                        }
                    }

                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("获取会员充值折扣", msg.obj.toString());
                    break;
            }
        }
    };
    CompoundButton.OnCheckedChangeListener onPayTypeSelectChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.cb_alipay:
                    if (isChecked == true) {
                        cbWechatPay.setChecked(false);
                    }
                    break;
                case R.id.cb_wechat_pay:
                    if (isChecked == true) {
                        cbAliPay.setChecked(false);
                    }
                    break;
            }
        }
    };

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            switch (buttonView.getId()) {
                case R.id.rb_one:
                    if (isChecked == true) {
                        ptbNum="10";
                        etInputNum.setText("");
                        if (vipOpen.getData().getDiscount().equals("0")){
                            tvPtbNum.setText(10+ "");
                        }else{
                            tvPtbNum.setText(10*(Integer.valueOf(vipOpen.getData().getDiscount())/10) + "");
                        }
                        rbTwo.setChecked(false);
                        rbThree.setChecked(false);
                        rbFour.setChecked(false);
                        rbFive.setChecked(false);
                        etInputNum.setBackgroundResource(R.drawable.radio_bg_ptb_num_nol);
                    }
                    break;
                case R.id.rb_two:
                    if (isChecked == true) {
                        ptbNum="50";
                        etInputNum.setText("");
                        if (vipOpen.getData().getDiscount().equals("0")){
                            tvPtbNum.setText(50+ "");
                        }else{
                            tvPtbNum.setText(50*(Integer.valueOf(vipOpen.getData().getDiscount())/10) + "");
                        }
                        rbOne.setChecked(false);
                        rbThree.setChecked(false);
                        rbFour.setChecked(false);
                        rbFive.setChecked(false);
                        etInputNum.setBackgroundResource(R.drawable.radio_bg_ptb_num_nol);
                    }
                    break;
                case R.id.rb_three:
                    ptbNum="100";
                    if (isChecked == true) {
                        etInputNum.setText("");
                        if (vipOpen.getData().getDiscount().equals("0")){
                            tvPtbNum.setText(100+ "");
                        }else{
                            tvPtbNum.setText(100*(Integer.valueOf(vipOpen.getData().getDiscount())/10) + "");
                        }
                        rbOne.setChecked(false);
                        rbTwo.setChecked(false);
                        rbFour.setChecked(false);
                        rbFive.setChecked(false);
                        etInputNum.setBackgroundResource(R.drawable.radio_bg_ptb_num_nol);
                    }
                    break;
                case R.id.rb_four:
                    ptbNum="300";
                    if (isChecked == true) {
                        etInputNum.setText("");
                        if (vipOpen.getData().getDiscount().equals("0")){
                            tvPtbNum.setText(300+ "");
                        }else{
                            tvPtbNum.setText(300*(Integer.valueOf(vipOpen.getData().getDiscount())/10) + "");
                        }
                        rbOne.setChecked(false);
                        rbTwo.setChecked(false);
                        rbThree.setChecked(false);
                        rbFive.setChecked(false);
                        etInputNum.setBackgroundResource(R.drawable.radio_bg_ptb_num_nol);
                    }
                    break;
                case R.id.rb_five:
                    ptbNum="500";
                    if (isChecked == true) {
                        etInputNum.setText("");
                        if (vipOpen.getData().getDiscount().equals("0")){
                            tvPtbNum.setText(500+ "");
                        }else{
                            tvPtbNum.setText(500*(Integer.valueOf(vipOpen.getData().getDiscount())/10) + "");
                        }
                        rbOne.setChecked(false);
                        rbTwo.setChecked(false);
                        rbThree.setChecked(false);
                        rbFour.setChecked(false);
                        etInputNum.setBackgroundResource(R.drawable.radio_bg_ptb_num_nol);
                    }
                    break;
            }
        }
    };


    TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
            Log.e("onTextChanged", s.toString());
            if (TextUtils.isEmpty(s)){
                tvPtbNum.setText(0+ "");
            }else{
                ptbNum=s.toString();
                if (vipOpen.getData().getDiscount().equals("0")){
                tvPtbNum.setText(s);
//                    tvPtbNum.setText(Double.valueOf(s.toString())*0.3 + "");
                    Log.e("pos1",Double.valueOf(s.toString())+"");
                }else{
                    tvPtbNum.setText(Integer.valueOf(s.toString())*(Integer.valueOf(vipOpen.getData().getDiscount())/10) + "");
                    Log.e("pos2",Integer.valueOf(vipOpen.getData().getDiscount())+"");
                }
            }

            if (s.toString().equals("")) {
                return;
            } else {
                setRadioCheckedFalse();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //编辑框内容变化之前会调用该方法，s为编辑框内容变化之前的内容
            Log.i("beforeTextChanged", s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
            Log.i("afterTextChanged", s.toString());

        }
    };


    private void setRadioCheckedFalse() {
        if (rbOne.isChecked() == true) {
            rbOne.setChecked(false);
        }
        if (rbTwo.isChecked() == true) {
            rbTwo.setChecked(false);
        }
        if (rbThree.isChecked() == true) {
            rbThree.setChecked(false);
        }
        if (rbFour.isChecked() == true) {
            rbFour.setChecked(false);
        }
        if (rbFive.isChecked() == true) {
            rbFive.setChecked(false);
        }
//        rbOne.setChecked(false);
//        rbTwo.setChecked(false);
//        rbThree.setChecked(false);
//        rbFour.setChecked(false);
//        rbFive.setChecked(false);
    }


    @Override
    protected void initDatas() {

    }


    @OnClick(R.id.tv_buy_ptb)
    public void onViewClicked() {
        String payNum = tvPtbNum.getText().toString();
        if (TextUtils.isEmpty(payNum) ) {
            ToastUtil.showToast("充值金额有误");
            return;
        }
        if (cbAliPay.isChecked()) {
            aliPay(payNum);
        } else {
            IWXAPI mWXApi = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, true);
            mWXApi.registerApp(Constant.WX_APP_ID);
            if (mWXApi.isWXAppInstalled()) {
                wxPay(payNum);
            } else {
                ToastUtil.showToast("未安装微信客户端");
            }
        }
    }

    private void wxPay(String payNum) {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getWXPayUrl());
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        params.addParameter("amount", payNum);
        params.addParameter("discount", vipOpen.getData().getDiscount());
        params.addParameter("num", ptbNum);
        Log.e("weixin",params.toString());
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils(mHandler);
        httpRequestUtils.doPost(params);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_SUCCESS:
                    Log.e("获取微信支付参数", msg.obj.toString());
                    WXPayEntity wxPayEntity = new Gson().fromJson(msg.obj.toString(), WXPayEntity.class);
                    if (wxPayEntity.getData() != null) {
                        PayReq req = new PayReq();
                        req.appId = wxPayEntity.getData().getAppid();
                        req.partnerId = wxPayEntity.getData().getPartnerid();
                        req.prepayId = wxPayEntity.getData().getPrepayid();
                        req.nonceStr = wxPayEntity.getData().getNoncestr();
                        req.timeStamp = wxPayEntity.getData().getTimestamp();
                        req.packageValue = wxPayEntity.getData().getPackageX();
                        req.sign = wxPayEntity.getData().getSign();
                        IWXAPI mWXApi = WXAPIFactory.createWXAPI(BuyPTBActivity.this, wxPayEntity.getData().getAppid(), true);
                        mWXApi.registerApp(Constant.WX_APP_ID);
                        mWXApi.sendReq(req);
                    }
                    break;
                case REQUEST_FAIL:
                    ToastUtil.showToast(msg.obj.toString());
                    break;
            }
        }
    };

    private void aliPay(String payNum) {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getAliPayUrl());
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        params.addParameter("amount", payNum);
        params.addParameter("discount", vipOpen.getData().getDiscount());
        params.addParameter("num", ptbNum);
        Log.e("weixin",params.toString());
        HttpRequestUtils httpRequestUtils = new HttpRequestUtils(aLiHandler);
        httpRequestUtils.doPost(params);
    }

    Handler aLiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_SUCCESS:
                    Log.e("获取支付宝支付参数", msg.obj.toString());
                    final AliPayEntity aliPayEntity = new Gson().fromJson(msg.obj.toString(), AliPayEntity.class);
                    if (aliPayEntity.getData() != null) {
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(BuyPTBActivity.this);
                                String result = alipay.pay(aliPayEntity.getData(), true);
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = result;
                                sHandler.sendMessage(msg);
                            }
                        };
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                    break;
                case REQUEST_FAIL:

                    break;
            }
        }
    };

/** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler sHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.e("PayResultStatus", payResult.toString() + "");
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(BuyPTBActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(BuyPTBActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(BuyPTBActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(BuyPTBActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
