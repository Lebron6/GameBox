package com.wag.gamebox.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.callback.GetVoucherResultCallBack;
import com.wag.gamebox.entity.GetVoucher;
import com.wag.gamebox.entity.MyPTB;
import com.wag.gamebox.entity.Servicer;
import com.wag.gamebox.entity.TaskList;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.activity.TaskActivity;
import com.wag.gamebox.ui.dialog.CallServiceDialog;

import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskListAdapter extends RecyclerView.Adapter {



    private Context context;
    private TaskList taskList;
    private  GetVoucherResultCallBack callBack;

    public TaskListAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(TaskList taskList, GetVoucherResultCallBack callBack) {
        this.taskList = taskList;
        this.callBack = callBack;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
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
        viewHolder.tvTaskContent.setText(taskList.getData().get(position).getName());
        viewHolder.tvTime.setText(taskList.getData().get(position).getStart_time()+"-"+taskList.getData().get(position).getEnd_time());
        if (taskList.getData().get(position).getStatus()==1){
            viewHolder.tvTaskStatus.setText("待完成");
            viewHolder.tvTaskStatus.setEnabled(false);
        }else if(taskList.getData().get(position).getStatus()==2){
            viewHolder.tvTaskStatus.setText("待领取"); viewHolder.tvTaskStatus.setEnabled(true);
        }else{
            viewHolder.tvTaskStatus.setText("已领取"); viewHolder.tvTaskStatus.setEnabled(false);
        }
            viewHolder.tvTaskStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams params = new RequestParams(BaseUrl.getInstence().getVoucherUrl());
                    params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
                    params.addParameter("id",taskList.getData().get(position).getId());
                    HttpRequestUtils requestUtils = new HttpRequestUtils(cHandler);
                    requestUtils.doGet(params);
                }
            });
    }
    private Handler cHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取代金券", msg.obj.toString());
                    GetVoucher servicer = new Gson().fromJson(msg.obj.toString(), GetVoucher.class);
                   if (servicer!=null){
                       if (servicer.getCode().equals("200")){
                            callBack.voucherResult(1);
                       }else{
                           ToastUtil.showToast(servicer.getMsg());
                       }
                   }else{
                       ToastUtil.showToast("领取奖励失败");
                   }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast("领取奖励失败");
                    Log.e("领取奖励失败", msg.obj.toString());
                    break;
            }
        }
    };
    public interface OnItemClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnOtemClickListener) {
        this.mOnOtemClickListener = mOnOtemClickListener;
    }

    private OnItemClickListener mOnOtemClickListener;


    @Override
    public int getItemCount() {
        return taskList.getData().size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_task_content)
        TextView tvTaskContent;
        @BindView(R.id.tv_task_status)
        TextView tvTaskStatus;
        @BindView(R.id.tv_time)
        TextView tvTime;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}