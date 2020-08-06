package com.wag.gamebox.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.entity.QueryHistory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QueryHistoryListAdapter extends RecyclerView.Adapter {



    private Context context;
    private List<QueryHistory> queryHistories;

    public QueryHistoryListAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<QueryHistory> queryHistories) {
        this.queryHistories = queryHistories;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_query_history, parent, false);
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
        viewHolder.tvGameName.setText(queryHistories.get(position).getKeyWord());

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
        return queryHistories.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_game_name)
        TextView tvGameName;
        @BindView(R.id.parent_layout)
        LinearLayout parentLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}