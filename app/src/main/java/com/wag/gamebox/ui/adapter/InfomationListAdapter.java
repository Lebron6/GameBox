package com.wag.gamebox.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.entity.InfoBean;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfomationListAdapter extends RecyclerView.Adapter {


    private Context context;
    private InfoBean infoBean;

    public InfomationListAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(InfoBean infoBean) {
        this.infoBean = infoBean;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_information, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        if (mOnClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onClickListener(v, position);
                }
            });
        }
        viewHolder.infoTime.setText(infoBean.getData().get(position).getCreate_time());
        viewHolder.tvTitle.setText(infoBean.getData().get(position).getTitle());
        viewHolder.tvDetails.setText(infoBean.getData().get(position).getContent());
        x.image().bind(viewHolder.ivIcon, BaseUrl.getInstence().ipAddress+infoBean.getData().get(position).getCover());
    }

    public interface OnClickListener {
        void onClickListener(View view, int position);
    }

    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    private OnClickListener mOnClickListener;


    @Override
    public int getItemCount() {
        return infoBean.getData().size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_details)
        TextView tvDetails;
        @BindView(R.id.info_time)
        TextView infoTime;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}