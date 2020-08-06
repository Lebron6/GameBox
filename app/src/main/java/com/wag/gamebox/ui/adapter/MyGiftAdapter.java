package com.wag.gamebox.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.entity.HomeGiftList;
import com.wag.gamebox.entity.MyGiftData;
import com.wag.gamebox.ui.activity.AllGiftOfGameActivity;
import com.wag.gamebox.ui.view.FilletImageView;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyGiftAdapter extends RecyclerView.Adapter {


    private Context context;
    private MyGiftData gifts;

    public MyGiftAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(MyGiftData gifts) {
        this.gifts = gifts;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gift, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemLongClick(v,position);
                }
            });
        }
        viewHolder.tvGameName.setText(gifts.getData().get(position).getGame_name());
        x.image().bind(viewHolder.ivGiftIcon, BaseUrl.getInstence().ipAddress+gifts.getData().get(position).getLogo_img());
        viewHolder.tvGiftNum.setText(gifts.getData().get(position).getCount()+"");
        viewHolder.tvGiftType.setText(gifts.getData().get(position).getList());
        viewHolder.tvSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllGiftOfGameActivity.actionStart(context,gifts.getData().get(position).getGame_name(),gifts.getData().get(position).getId());
            }
        });

    }

    public interface OnItemClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemLongClickListener) {
        this.onItemClickListener = mOnItemLongClickListener;
    }

    private OnItemClickListener onItemClickListener;


    @Override
    public int getItemCount() {
        return gifts.getData().size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_gift_icon)
        FilletImageView ivGiftIcon;
        @BindView(R.id.tv_game_name)
        TextView tvGameName;
        @BindView(R.id.tv_gift_num)
        TextView tvGiftNum;
        @BindView(R.id.tv_gift_type)
        TextView tvGiftType;
        @BindView(R.id.tv_see)
        TextView tvSee;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}