package com.wag.gamebox.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.entity.MyPTB;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PTBListAdapter extends RecyclerView.Adapter {



    private Context context;
    private MyPTB myPTB;

    public PTBListAdapter(Context context) {
        this.context = context;
    }

    public void setDatas( MyPTB myPTB) {
        this.myPTB = myPTB;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ptb_buy_list, parent, false);
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
        viewHolder.tvPtbNum.setText(myPTB.getData().getList().get(position).getCoin());
        viewHolder.tvPtbTime.setText(myPTB.getData().getList().get(position).getCreate_time());
        viewHolder.tvPtbGetType.setText(myPTB.getData().getList().get(position).getInfo());

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
        return myPTB.getData().getList().size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ptb_num)
        TextView tvPtbNum;
        @BindView(R.id.tv_ptb_get_type)
        TextView tvPtbGetType;
        @BindView(R.id.tv_ptb_time)
        TextView tvPtbTime;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}