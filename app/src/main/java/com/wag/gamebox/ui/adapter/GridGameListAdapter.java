package com.wag.gamebox.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.callback.DownloadObserver;
import com.wag.gamebox.entity.GameInfo;
import com.wag.gamebox.tools.DbUtils;
import com.wag.gamebox.tools.DownLoadManger;
import com.wag.gamebox.ui.view.FilletImageView;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridGameListAdapter extends RecyclerView.Adapter implements DownloadObserver {


    private List<ViewHolder> mHolders = new LinkedList<ViewHolder>();
    List<GameInfo> gameInfos;
    private Context context;
    private static DbManager db;
    private GameInfo gameInfo;
    private ViewHolder viewHolder;

    public GridGameListAdapter(Context context) {
        this.context = context;
        db = DbUtils.getDB();
    }

    public void setDatas(List<GameInfo> dataBean) {
        gameInfos=new ArrayList<>();
        gameInfos.addAll(dataBean);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_hot_game, parent, false);
        return new ViewHolder(view, gameInfos);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        gameInfo = verifLocalIsHave(gameInfos.get(position));   //根据游戏列表返回Id查询数据库比对，筛选出有下载记录的游戏
        viewHolder = (ViewHolder) holder;
        viewHolder.setPosition(position);

        if (mOnOtemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnOtemClickListener.onItemLongClick(v, position);
                }
            });
        }
        ImageOptions imageOptions = (new ImageOptions.Builder()).setImageScaleType(ImageView.ScaleType.CENTER_CROP) //设置加载过程中的图片
                .setLoadingDrawableId(R.mipmap.icon_status).setIgnoreGif(false).build();
        x.image().bind(viewHolder.ivPic, BaseUrl.getInstence().ipAddress + gameInfos.get(position).getImg(),imageOptions);
        viewHolder.tvGameName.setText(gameInfos.get(position).getGame_name());
        viewHolder.homeGameSize.setText(gameInfos.get(position).getGame_size());
        viewHolder.rbBar.setRating(gameInfos.get(position).getLevel());
        viewHolder.tvDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("点击下载位置", gameInfos.get(position).toString());
                downLoadClick(gameInfos.get(position));
            }
        });

        mHolders.add(viewHolder);
        viewHolder.initStatus(gameInfo);        //更新下载显示状态
    }

    public void startDownLoad(GameInfo app) {
        DownLoadManger.getInstance().down(app);
    }

    public void PuseDownLoad(GameInfo app) {
        DownLoadManger.getInstance().pause(app);
    }

    public void InStall(GameInfo app) {
        DownLoadManger.getInstance().install(app);
    }

    public void Open(GameInfo app) {
        DownLoadManger.getInstance().open(app);
    }

    private void downLoadClick(GameInfo gameInfo) {
        GameInfo down1 = verifLocalIsHave(gameInfo);
        switch (down1.getBtn_tatus()) {
            case DownLoadManger.DOWNLOAD_NOT:
                startDownLoad(down1);
                break;
            case DownLoadManger.DOWNLOAD_LOADING:
                PuseDownLoad(down1);
                break;
            case DownLoadManger.DOWNLOAD_SUCCESS:
                InStall(down1);
                break;
            case DownLoadManger.DOWNLOAD_FAILED:
                startDownLoad(down1);
                break;
            case DownLoadManger.DOWNLOAD_INSTALL:
                Open(down1);
                break;
            case DownLoadManger.DOWNLOAD_PAUSE:
                startDownLoad(down1);
                break;
        }
    }


    public static GameInfo verifLocalIsHave(GameInfo gameInfo) {
        try {
            GameInfo byId = db.findById(GameInfo.class, gameInfo.getId());
            if (byId == null) {             //若本地没有下载记录，重新下载该游戏，存入数据库
                return gameInfo;
            } else {
                Log.e("byid",byId.toString());
                return byId;
            }
        } catch (DbException e) {
            e.printStackTrace();
            Log.e("查询数据库GameInfo异常", e.toString());
            return null;
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
        if (gameInfos == null || gameInfos.size() <= 0) {
            return 0;
        }
        return gameInfos.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_pic)
        FilletImageView ivPic;
        @BindView(R.id.tv_game_name)
        TextView tvGameName;
        @BindView(R.id.tv_down_load)
        TextView tvDownLoad;
        @BindView(R.id.home_game_size)
        TextView homeGameSize;
        @BindView(R.id.rb_bar)
        RatingBar rbBar;
        int mPosition=0;
        private List<GameInfo> gameInfos;

        public ViewHolder(View view, List<GameInfo> gameInfos) {
            super(view);
            ButterKnife.bind(this, view);
            this.gameInfos = gameInfos;
        }

        public void setPosition(int position) {
            this.mPosition = position;
        }

        public void Refresh(GameInfo appInfo) {
            if (appInfo.getId() == gameInfos.get(mPosition).getId()) {
                initStatus(appInfo);
                Log.e("Holder里的Refresh方法是否执行了", gameInfos.get(mPosition).getId() + "");//修改这里
            }
        }

        public void an() {
            GameInfo down = verifLocalIsHave(gameInfos.get(mPosition));
            DownLoadManger.getInstance().isInstall(down);
        }
        public void initStatus(GameInfo app) {
            switch (app.getBtn_tatus()) {
                case DownLoadManger.DOWNLOAD_WAITING:
                    tvDownLoad.setText("等待...");
                    break;
                case DownLoadManger.DOWNLOAD_NOT:
                    tvDownLoad.setText("下载");
                    break;
                case DownLoadManger.DOWNLOAD_LOADING:
                    int progress = (int) (app.getProgress() * 100f / app.getZsize() + 0.5f);
                    tvDownLoad.setText(progress + "%");
                    break;
                case DownLoadManger.DOWNLOAD_SUCCESS:
                    tvDownLoad.setText("安装");
                    break;
                case DownLoadManger.DOWNLOAD_FAILED:
                    tvDownLoad.setText("重试");
                    break;
                case DownLoadManger.DOWNLOAD_INSTALL:
                    tvDownLoad.setText("打开");
                    break;
                case DownLoadManger.DOWNLOAD_PAUSE:
                    tvDownLoad.setText("继续");
                    break;
            }
        }
    }


    public void start() {
        DownLoadManger.getInstance().addObserver(this);
    }

    public void delete() {
        DownLoadManger.getInstance().deleteObserver(this);
    }

    @Override
    public void onDownloadStateChanged(DownLoadManger manager, GameInfo info) {
        ListIterator<ViewHolder> iterator = mHolders.listIterator();
        while (iterator.hasNext()) {
            ViewHolder holder = iterator.next();
            Log.e("Adapter里是否调用了Holder的刷新", "true");
            holder.Refresh(info);
        }
    }

    public void state() {
        ListIterator<ViewHolder> iterator = mHolders.listIterator();
        while (iterator.hasNext()) {
            ViewHolder holder = iterator.next();
            holder.an();
        }
    }

}