package com.wag.gamebox.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.entity.MyPTB;
import com.wag.gamebox.entity.Voucher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VoucherListAdapter extends RecyclerView.Adapter {



    private Context context;
    private Voucher voucher;

    public VoucherListAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(Voucher voucher) {
        this.voucher = voucher;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_voucher, parent, false);
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
        viewHolder.tvPtbNum.setText(voucher.getData().get(position).getCoupon());
        viewHolder.tvCouse.setText(voucher.getData().get(position).getExplain());
        viewHolder.tvVoucherTime.setText(voucher.getData().get(position).getStart_time()+"-"+voucher.getData().get(position).getEnd_time());
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
        return voucher.getData().size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ptb_num)
        TextView tvPtbNum;
        @BindView(R.id.tv_couse)
        TextView tvCouse;
        @BindView(R.id.tv_voucher_time)
        TextView tvVoucherTime;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}