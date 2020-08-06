package com.wag.gamebox.ui.fragment.gdetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.callback.CommentCallListener;
import com.wag.gamebox.entity.CallBackResult;
import com.wag.gamebox.entity.GameDetails;
import com.wag.gamebox.entity.GameInfo;
import com.wag.gamebox.tools.DownLoadManger;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.activity.CommentDetailsActivity;
import com.wag.gamebox.ui.adapter.CommentListAdapter;
import com.wag.gamebox.ui.dialog.InputTextMsgDialog;
import com.wag.gamebox.ui.fragment.BaseFragment;
import com.wag.gamebox.ui.view.LoadingManger;

import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.wag.gamebox.ui.activity.GameDeatailsActivity.GAME_ID;

/**
 * Created by James on 2018/9/29.
 */
@SuppressLint("ValidFragment")
public class Comment extends BaseFragment{


    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
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
    CommentListAdapter adapter;
    private GameDetails gameDetails;


    public Comment() {
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_gamedetails_comment;
    }

    @Override
    protected void initViews() {
        LoadingManger.getInsetance().setView(error);
        adapter=new CommentListAdapter(getActivity(), getActivity().getIntent().getIntExtra(GAME_ID, -1));adapter.setOnItemClickListener(onItemClickListener);
    }
    CommentListAdapter.OnItemClickListener onItemClickListener=new CommentListAdapter.OnItemClickListener() {
        @Override
        public void onItemLongClick(View view, final int position) {
            InputTextMsgDialog inputTextMsgDialog = new InputTextMsgDialog(getActivity(), R.style.MyDialog);
            inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg) {
                    //点击发送按钮后，回调此方法，msg为输入的值
                    if (!TextUtils.isEmpty(msg)) {
                        comment(msg,gameDetails.getData().getComments().get(position).getId(),0);
                    }
                }
            });
            inputTextMsgDialog.show();
        }
    };

    private void comment(String msg,int pid,int callBackUserId) {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getCommentCallBackUrl());
        params.addParameter("game_id", getActivity().getIntent().getIntExtra(GAME_ID, -1));
        params.addParameter("pid", pid);
        params.addParameter("back_id", callBackUserId);
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        params.addParameter("content", msg);
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
                            getAllCommentByGame();
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

    @Override
    protected void initDatas() {
        LoadingManger.getInsetance().startLoading();
        getAllCommentByGame();
    }


    private void getAllCommentByGame() {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getGameDetailsUrl());
        params.addParameter("id", getActivity().getIntent().getIntExtra(GAME_ID, -1));
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
        requestUtils.doGet(params);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取游戏评论", msg.obj.toString());
                    try {
                        gameDetails = new Gson().fromJson(msg.obj.toString(), GameDetails.class);
                            if (gameDetails != null && gameDetails.getData() != null) {
                                if (gameDetails.getData().getGame_details() != null ) {
                                    adapter.setDatas(gameDetails.getData());
                                    RecyclerViewHelper.initRecyclerViewV(getActivity(),rvComment,false,adapter,false);
                                    LoadingManger.getInsetance().stopFinishLoading(true);
                                }else{
                                    LoadingManger.getInsetance().stopFinishLoading("暂无评论", false);
                                }
                            }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LoadingManger.getInsetance().stopFinishLoading("数据解析异常", false);
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("获取游戏所有评论数据", msg.obj.toString());
                    LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
                    break;
            }
        }
    };

    public void sendMoment(int code){
        if (code==1){
            getAllCommentByGame();
        }
    }

}
