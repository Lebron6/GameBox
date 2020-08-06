package com.wag.gamebox.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.entity.GameDetails;
import com.wag.gamebox.tools.RecyclerViewHelper;
import com.wag.gamebox.ui.activity.CommentDetailsActivity;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wag.gamebox.ui.activity.GameDeatailsActivity.GAME_ID;

public class CommentListAdapter extends RecyclerView.Adapter {


    private Context context;
    private int gameId;
    private GameDetails.DataBean dataBean;

    public CommentListAdapter(Context context,int gameId) {
        this.context = context;
        this.gameId = gameId;
    }

    public void setDatas(GameDetails.DataBean dataBean) {
        this.dataBean = dataBean;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
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
        x.image().bind(viewHolder.ivUserIcon, dataBean.getComments().get(position).getHead_img(), imageOptions);
        viewHolder.tvCommentTime.setText(dataBean.getComments().get(position).getTime());
        viewHolder.tvComment.setText(dataBean.getComments().get(position).getContent());
        viewHolder.tvUserName.setText(dataBean.getComments().get(position).getUser_name());
        if (dataBean.getComments().get(position).getList().size() == 0) {
            viewHolder.layoutCallback.setVisibility(View.GONE);
            viewHolder.layoutAllCallback.setVisibility(View.GONE);
        } else if (dataBean.getComments().get(position).getList().size() > 3) {
            viewHolder.layoutCallback.setVisibility(View.VISIBLE);
            viewHolder.layoutAllCallback.setVisibility(View.VISIBLE);
        } else {
            viewHolder.layoutAllCallback.setVisibility(View.GONE);
            viewHolder.layoutCallback.setVisibility(View.VISIBLE);
        }
        viewHolder.tvCallbackNum.setText(dataBean.getComments().get(position).getNum() + "");
        CommentCallBackAdapter adapter = new CommentCallBackAdapter(context);
        adapter.setDatas(dataBean.getComments().get(position).getList());
        RecyclerViewHelper.initRecyclerViewV(context, viewHolder.rvCallBack, false, adapter);
        viewHolder.layoutAllCallback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CommentDetailsActivity.actionStart(context, dataBean.getComments().get(position).getId(),gameId );
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
        return dataBean.getComments().size();
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
        @BindView(R.id.rv_call_back)
        RecyclerView rvCallBack;
        @BindView(R.id.tv_callback_num)
        TextView tvCallbackNum;
        @BindView(R.id.layout_all_callback)
        LinearLayout layoutAllCallback;
        @BindView(R.id.layout_callback)
        LinearLayout layoutCallback;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}