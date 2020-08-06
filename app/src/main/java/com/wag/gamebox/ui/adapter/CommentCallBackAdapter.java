package com.wag.gamebox.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.entity.GameDetails;
import com.wag.gamebox.ui.activity.CommentDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentCallBackAdapter extends RecyclerView.Adapter {



    private Context context;
    private List<GameDetails.DataBean.CommentsBean.ListBean> list;

    public CommentCallBackAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<GameDetails.DataBean.CommentsBean.ListBean> list) {
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_callback, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvCallName.setText(list.get(position).getUser_name());
        viewHolder.tvCallTxt.setText(" ："+list.get(position).getContent());
        if (mOnOtemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    mOnOtemClickListener.onItemLongClick(v, position);
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnOtemClickListener) {
        this.mOnOtemClickListener = mOnOtemClickListener;
    }

    private OnItemClickListener mOnOtemClickListener;


    @Override
    public int getItemCount() {
        if (list.size()<3){
            return list.size();
        }else{
            return 3;
        }

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_call_name)
        TextView tvCallName;
        @BindView(R.id.tv_call_txt)
        TextView tvCallTxt;
        @BindView(R.id.layout_callback)
        LinearLayout layoutCallback;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}