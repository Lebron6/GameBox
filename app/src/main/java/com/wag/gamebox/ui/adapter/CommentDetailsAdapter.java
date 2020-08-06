package com.wag.gamebox.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.entity.CommentDetails;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentDetailsAdapter extends RecyclerView.Adapter {


    private Context context;
    private CommentDetails commentDetails;

    public CommentDetailsAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(CommentDetails commentDetails) {
        this.commentDetails = commentDetails;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        ImageOptions imageOptions = (new ImageOptions.Builder()).setImageScaleType(ImageView.ScaleType.CENTER_CROP).setCircular(true).setCrop(true).setIgnoreGif(false).build();
        x.image().bind(viewHolder.ivUserIcon, commentDetails.getData().getList().get(position).getHead_img(),imageOptions);
        viewHolder.tvComment.setText(commentDetails.getData().getList().get(position).getContent());
        if (TextUtils.isEmpty(commentDetails.getData().getList().get(position).getBack_user_name())){
            viewHolder.tvUserName.setText(commentDetails.getData().getList().get(position).getUser_name());
        }else{
            viewHolder.tvUserName.setText(commentDetails.getData().getList().get(position).getUser_name() + " " + "回复" + " " + commentDetails.getData().getList().get(position).getBack_user_name());
        }
        viewHolder.tvCommentTime.setText(commentDetails.getData().getList().get(position).getTime());
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
        return commentDetails.getData().getList().size();
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

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}