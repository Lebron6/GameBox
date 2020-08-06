package com.wag.gamebox.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.entity.MyComment;
import com.wag.gamebox.ui.activity.CommentDetailsActivity;
import com.wag.gamebox.ui.activity.GameDeatailsActivity;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCommentListAdapter extends RecyclerView.Adapter {



    private Context context;
    private List<MyComment.DataBean> data;

    public MyCommentListAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<MyComment.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_comment, parent, false);
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

        ImageOptions imageOptions = (new ImageOptions.Builder()).setImageScaleType(ImageView.ScaleType.CENTER_CROP).setCircular(true).setCrop(true).setIgnoreGif(false).build();
        x.image().bind(viewHolder.ivUserIcon, data.get(position).getHead_img(), imageOptions);
        x.image().bind(viewHolder.ivGameIcon, BaseUrl.getInstence().ipAddress+data.get(position).getLogo_img());
        viewHolder.tvComment.setText(data.get(position).getContent());
        viewHolder.tvUserName.setText(data.get(position).getUser_name());
        if (data.get(position).getBack()>0){
            viewHolder.tvCallbackNum.setText(data.get(position).getBack() + "");
        }else{
            viewHolder.layoutAllCallback.setVisibility(View.GONE);
        }

        viewHolder.tvGameName.setText(data.get(position).getGame_name() + "");
        viewHolder.layoutGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameDeatailsActivity.actionStart(context, data.get(position).getGame_id());
            }
        });

        viewHolder.layoutAllCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentDetailsActivity.actionStart(context, data.get(position).getId(), data.get(position).getGame_id());
            }
        });
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
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_user_icon)
        ImageView ivUserIcon;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_comment_time)
        TextView tvCommentTime;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.tv_callback_num)
        TextView tvCallbackNum;
        @BindView(R.id.layout_all_callback)
        LinearLayout layoutAllCallback;
        @BindView(R.id.tv_game_name)
        TextView tvGameName;
        @BindView(R.id.iv_game_icon)
        ImageView ivGameIcon;
        @BindView(R.id.layout_game)
        RelativeLayout layoutGame;
        @BindView(R.id.layout_callback)
        LinearLayout layoutCallback;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}