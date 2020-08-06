package com.wag.gamebox.ui.fragment;

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
import com.wag.gamebox.entity.VersionInfo;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.Utils;
import com.wag.gamebox.tools.VersionUpdataUtils;
import com.wag.gamebox.ui.activity.AboutUsActivity;
import com.wag.gamebox.ui.activity.FeedBackActivity;
import com.wag.gamebox.ui.activity.LoginActivity;
import com.wag.gamebox.ui.activity.UpdataDataMangerActivity;
import com.wag.gamebox.ui.activity.UserInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by James on 2019/5/21.
 */
public class Manger extends BaseFragment {

    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_sreach)
    ImageView ivSreach;
    @BindView(R.id.layout_top)
    RelativeLayout layoutTop;
    @BindView(R.id.layout_updata_manger)
    RelativeLayout layoutUpdataManger;
    @BindView(R.id.layout_settings)
    RelativeLayout layoutSettings;
    @BindView(R.id.layout_feed_back)
    RelativeLayout layoutFeedBack;
    @BindView(R.id.layout_v_updata)
    RelativeLayout layoutVUpdata;
    @BindView(R.id.layout_about)
    RelativeLayout layoutAbout;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_manger;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @OnClick({R.id.layout_updata_manger, R.id.layout_settings, R.id.layout_feed_back, R.id.layout_v_updata, R.id.layout_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_updata_manger:
                UpdataDataMangerActivity.actionStart(getActivity());
                break;
            case R.id.layout_settings:
                if (!PreferenceUtils.getInstance().getLoginStatus()) {
                    LoginActivity.actionStart(getActivity());
                } else {
                    UserInfoActivity.actionStart(getActivity());
                }
                break;
            case R.id.layout_feed_back:
                FeedBackActivity.actionStart(getActivity());
                break;
            case R.id.layout_v_updata:
                checkVersionInformation();
                break;
            case R.id.layout_about:
                AboutUsActivity.actionStart(getActivity());
                break;
        }
    }

    private void checkVersionInformation() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getVersionsUrl());
        params.addParameter("versions", Utils.getLocalVersion(getActivity()));
        params.addParameter("type", "android");
        HttpRequestUtils requestUtils = new HttpRequestUtils(sHandler);
        requestUtils.doGet(params);
    }


    private Handler sHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取版本数据", msg.obj.toString());
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(msg.obj.toString());
                        if (jsonObject.getString("code").equals("200")) {
                            ToastUtil.showToast("已是最新版本,无需下载");
                        } else if (jsonObject.getString("code").equals("300")) {
                            VersionInfo info = new Gson().fromJson(msg.obj.toString(), VersionInfo.class);
                            if (TextUtils.isEmpty(info.getData().getAndroid_url())) {
                                ToastUtil.showToast("新版本更新链接有误！");
                                return;
                            }
                            VersionUpdataUtils.update(info.getData().getAndroid_url(), getActivity(), 1);
                        } else {
                            ToastUtil.showToast("无效响应Code:" + jsonObject.getString("code"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast("获取版本数据失败");
                    Log.e("获取版本数据", msg.obj.toString());
                    break;
            }
        }
    };

}
