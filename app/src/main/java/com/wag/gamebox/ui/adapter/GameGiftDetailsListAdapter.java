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
import com.wag.gamebox.tools.Utils;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GameGiftDetailsListAdapter extends RecyclerView.Adapter {



    private Context context;
    List<GameDetails.DataBean.KacListBean> kac_list;

    public GameGiftDetailsListAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<GameDetails.DataBean.KacListBean> kac_list) {
        this.kac_list = kac_list;
        notifyDataSetChanged();
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
        if (kac_list.get(position).getStatus() == 1) {//未领取
            viewHolder.tvGet.setVisibility(View.VISIBLE);
            viewHolder.tvCopy.setVisibility(View.GONE);
            viewHolder.layoutNum.setVisibility(View.VISIBLE);
            viewHolder.layoutCdkey.setVisibility(View.GONE);
            viewHolder.tvGiftName.setText(kac_list.get(position).getName());
            viewHolder.tvGiftType.setText(kac_list.get(position).getContent());
            viewHolder.tvGiftNum.setText(kac_list.get(position).getResidue()+"");
        } else {
            viewHolder.tvGet.setVisibility(View.GONE);
            viewHolder.tvCopy.setVisibility(View.VISIBLE);
            viewHolder.layoutNum.setVisibility(View.GONE);
            viewHolder.layoutCdkey.setVisibility(View.VISIBLE);
            viewHolder.tvGiftCode.setText(kac_list.get(position).getCdkey());
            viewHolder.tvGiftName.setText(kac_list.get(position).getName());
            viewHolder.tvGiftType.setText(kac_list.get(position).getContent());
        }
        viewHolder.tvCopy.setOnClickListener(new View.OnClickListener() {       //复制
            @Override
            public void onClick(View v) {
                Utils.copy(kac_list.get(position).getCdkey());
            }
        });
        viewHolder.tvGet.setOnClickListener(new View.OnClickListener() {        //领取礼包
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener mOnOtemClickListener) {
        this.mOnOtemClickListener = mOnOtemClickListener;
    }

    public interface OnItemClickListener {
        void onItemLongClick(View view, int position);
    }


    private OnItemClickListener mOnOtemClickListener;


    @Override
    public int getItemCount() {
        return kac_list.size();
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