package com.wag.gamebox.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.callback.OnGiftGetSucCallBack;
import com.wag.gamebox.entity.AllGiftsOfGame;
import com.wag.gamebox.entity.GameDetails;
import com.wag.gamebox.entity.GetGiftResult;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.Utils;
import com.wag.gamebox.ui.activity.GiftDetailsActivity;
import com.wag.gamebox.ui.activity.LoginActivity;
import com.wag.gamebox.ui.dialog.GetGiftSuccessDialog;

import org.xutils.http.RequestParams;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllGiftOfGameAdapter extends RecyclerView.Adapter {



    private Activity context;
    AllGiftsOfGame allGiftsOfGame;
OnGiftGetSucCallBack callBack;

    public AllGiftOfGameAdapter(Activity context) {
        this.context = context;
    }

    public void setDatas(AllGiftsOfGame allGiftsOfGame) {
        this.allGiftsOfGame = allGiftsOfGame;
        notifyDataSetChanged();
    }

    public void setGetGiftSucCallBack(OnGiftGetSucCallBack callBack){
        this.callBack=callBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gamedetails_gift, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        if (mOnOtemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    mOnOtemClickListener.onItemLongClick(v, position);
                }
            });
        }
        if (allGiftsOfGame.getData().getKac_list().get(position).getStatus() == 1) {//未领取
            viewHolder.tvGet.setVisibility(View.VISIBLE);
            viewHolder.tvCopy.setVisibility(View.GONE);
            viewHolder.layoutNum.setVisibility(View.VISIBLE);
            viewHolder.layoutCdkey.setVisibility(View.GONE);
            viewHolder.tvGiftName.setText(allGiftsOfGame.getData().getKac_list().get(position).getName());
            viewHolder.tvGiftType.setText(allGiftsOfGame.getData().getKac_list().get(position).getContent());
            viewHolder.tvGiftNum.setText(allGiftsOfGame.getData().getKac_list().get(position).getResidue()+"");
        } else {
            viewHolder.tvGet.setVisibility(View.GONE);
            viewHolder.tvCopy.setVisibility(View.VISIBLE);
            viewHolder.layoutNum.setVisibility(View.GONE);
            viewHolder.layoutCdkey.setVisibility(View.VISIBLE);
            viewHolder.tvGiftCode.setText(allGiftsOfGame.getData().getKac_list().get(position).getCdkey());
            viewHolder.tvGiftName.setText(allGiftsOfGame.getData().getKac_list().get(position).getName());
            viewHolder.tvGiftType.setText(allGiftsOfGame.getData().getKac_list().get(position).getContent());
        }
        viewHolder.tvCopy.setOnClickListener(new View.OnClickListener() {       //复制
            @Override
            public void onClick(View v) {
                Utils.copy(allGiftsOfGame.getData().getKac_list().get(position).getCdkey());
            }
        });
        viewHolder.tvGet.setOnClickListener(new View.OnClickListener() {        //领取礼包
            @Override
            public void onClick(View v) {
                if (PreferenceUtils.getInstance().getLoginStatus()==true){
                    getGiftCode(position);
                }else{
                    LoginActivity.actionStart(context);
                    context.finish();
                }
            }
        });
    }

    private void getGiftCode(int position) {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getReceiveGiftUrl());
        params.addParameter("kac_id", allGiftsOfGame.getData().getKac_list().get(position).getKac_id());
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
                            callBack.onGiftGetSucCallBack();
                        }
                        ToastUtil.showToast(result.getMsg());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("数据解析异常", e.toString());
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    Log.e("领取礼包结果", msg.obj.toString());
                    break;
            }
        }
    };

    public void setOnItemClickListener(OnItemClickListener mOnOtemClickListener) {
        this.mOnOtemClickListener = mOnOtemClickListener;
    }

    public interface OnItemClickListener {
        void onItemLongClick(View view, int position);
    }


    private OnItemClickListener mOnOtemClickListener;


    @Override
    public int getItemCount() {
        return allGiftsOfGame.getData().getKac_list().size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_gift_name)
        TextView tvGiftName;
        @BindView(R.id.tv_gift_num)
        TextView tvGiftNum;
        @BindView(R.id.tv_gift_code)
        TextView tvGiftCode;
        @BindView(R.id.tv_gift_type)
        TextView tvGiftType;
        @BindView(R.id.tv_get)
        TextView tvGet;
        @BindView(R.id.tv_copy)
        TextView tvCopy;
        @BindView(R.id.layout_num)
        LinearLayout layoutNum;
        @BindView(R.id.layout_cdkey)
        LinearLayout layoutCdkey;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}