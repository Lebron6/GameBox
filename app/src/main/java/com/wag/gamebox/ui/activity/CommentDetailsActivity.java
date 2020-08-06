package com.wag.gamebox.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
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
import com.wag.gamebox.entity.CallBackResult;
import com.wag.gamebox.entity.CommentDetails;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.adapter.CommentDetailsAdapter;
import com.wag.gamebox.ui.dialog.InputTextMsgDialog;
import com.wag.gamebox.ui.view.LoadingManger;
import com.wag.gamebox.ui.view.TitleBarManger;

import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wag.gamebox.ui.activity.GameDeatailsActivity.GAME_ID;

/**
 * 评论详情
 * Created by James on 2018/10/29.
 */
public class CommentDetailsActivity extends BaseActivity {

    CommentDetailsAdapter adapter;
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
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_comment_time)
    TextView tvCommentTime;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.rv_callback)
    RecyclerView rvCallback;
    @BindView(R.id.msg)
    TextView msg;
    @BindView(R.id.res)
    LinearLayout res;
    @BindView(R.id.loading)
    ImageView loading;
    @BindView(R.id.layout_loading)
    LinearLayout layoutLoading;
    @BindView(R.id.error)
    RelativeLayout error;
    @BindView(R.id.tv_callback)
    TextView tvCallback;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;

    public static final String COMMENT_ID = "commentId";
    public static final String GAME_ID = "gameId";
    private CommentDetails commentDetails;

    public static void actionStart(Context context, int commentId, int gameId) {
        Intent intent = new Intent(context, CommentDetailsActivity.class);
        intent.putExtra(COMMENT_ID, commentId);
        intent.putExtra(GAME_ID, gameId);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("评论详情");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_comment_details;
    }

    @Override
    protected void initViews() {
        adapter = new CommentDetailsAdapter(this);
        adapter.setOnItemClickListener(onItemClickListener);
        LoadingManger.getInsetance().setView(error);
    }

    @Override
    protected void initDatas() {
        LoadingManger.getInsetance().startLoading();
        getCommentDetailsById();
    }

    private void getCommentDetailsById() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getCommentDeatailsUrl());
        params.addParameter("id", getIntent().getIntExtra(COMMENT_ID, -1));
        HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
        requestUtils.doGet(params);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取评论详情数据", msg.obj.toString());
                    try {
                        commentDetails = new Gson().fromJson(msg.obj.toString(), CommentDetails.class);
                        if (commentDetails != null) {
                            ImageOptions imageOptions = (new ImageOptions.Builder()).setImageScaleType(ImageView.ScaleType.CENTER_CROP).setCircular(true).setCrop(true).setIgnoreGif(false).build();
                            x.image().bind(ivUserIcon, commentDetails.getData().getHead_img(), imageOptions);
                            tvUserName.setText(commentDetails.getData().getUser_name());
                            tvCommentTime.setText(commentDetails.getData().getTime());
                            tvComment.setText(commentDetails.getData().getContent());
                            if (commentDetails.getData().getList() != null && commentDetails.getData().getList().size() > 0) {
                                adapter.setDatas(commentDetails);
                                RecyclerViewHelper.initRecyclerViewV(CommentDetailsActivity.this, rvCallback, false, adapter, true);
                                LoadingManger.getInsetance().stopFinishLoading(true);
                            } else {
                                LoadingManger.getInsetance().stopFinishLoading("暂无评论", false);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LoadingManger.getInsetance().stopFinishLoading("数据解析异常", false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("获取评论详情数据", msg.obj.toString());
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
                    break;
            }
        }
    };
    private String callBackUserId;
    private String callBackUserName;
    CommentDetailsAdapter.OnItemClickListener onItemClickListener=new CommentDetailsAdapter.OnItemClickListener() {
        @Override
        public void onItemLongClick(View view, int position) {
            callBackUserId=commentDetails.getData().getList().get(position).getUser_id();
            callBackUserName=commentDetails.getData().getList().get(position).getUser_name();
            InputTextMsgDialog inputTextMsgDialog = new InputTextMsgDialog(CommentDetailsActivity.this, R.style.MyDialog);
            inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg) {
                    //点击发送按钮后，回调此方法，msg为输入的值
                    if (!TextUtils.isEmpty(msg)) {
                        tvCallback.setText("回复 "+callBackUserName+":"+msg);
                    }
                }
            });
            inputTextMsgDialog.show();
        }
    };
    @OnClick({R.id.tv_callback, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_callback:

                break;
            case R.id.tv_send:
                if (TextUtils.isEmpty(tvComment.getText().toString())) {
                    ToastUtil.showToast("请输入评论内容");
                    return;
                } else {
                    comment(tvCallback.getText().toString());
                }
                break;
        }
    }

    private void comment(String msg) {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getCommentCallBackUrl());
        params.addParameter("game_id", getIntent().getIntExtra(GAME_ID, -1));
        params.addParameter("pid", getIntent().getIntExtra(COMMENT_ID, -1));
        params.addParameter("back_id", callBackUserId);
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        params.addParameter("content", msg.substring(callBackUserName.length()+4,msg.length()));
        HttpRequestUtils requestUtils = new HttpRequestUtils(cHandler);
        requestUtils.doGet(params);
    }

    private Handler cHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("回复评论详情", msg.obj.toString());
                    try {
                        CallBackResult result = new Gson().fromJson(msg.obj.toString(), CallBackResult.class);
                        if (result.getCode().equals("200")) {
                            ToastUtil.showToast("回复成功");
                            tvCallback.setText("");
                            getCommentDetailsById();
                        } else {
                            ToastUtil.showToast(result.getMsg());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LoadingManger.getInsetance().stopFinishLoading("回复评论详情数据解析异常", false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("回复评论详情失败数据", msg.obj.toString());
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
                    break;
            }
        }
    };
}
