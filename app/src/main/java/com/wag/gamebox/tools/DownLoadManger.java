package com.wag.gamebox.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.wag.gamebox.app.WagGameBoxApp;
import com.wag.gamebox.callback.DownloadObserver;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.Executor;

import com.wag.gamebox.entity.GameInfo;

/**
 * Created by Administrator on 2016/7/15.
 */
public class DownLoadManger {

    public static final int DOWNLOAD_NOT = 0;//未下载
    public static final int DOWNLOAD_WAITING = 1;//等待
    public static final int DOWNLOAD_LOADING = 2;//下载中
    public static final int DOWNLOAD_SUCCESS = 3;//下载成功未安装
    public static final int DOWNLOAD_FAILED = 4;//下载失败
    public static final int DOWNLOAD_INSTALL = 5;//已安装
    public static final int DOWNLOAD_PAUSE = 6;//暂停
    private static DownLoadManger instance;
    private static DbManager db;
    private final Executor exec = new PriorityExecutor(1, true);//成员常量
    private static List<DownloadObserver> mObservers = new LinkedList<DownloadObserver>();
    private static Map<String, DownloadInfo> mDownloadInfos = new HashMap<String, DownloadInfo>();
    private List<GameInfo> XiaAppList;


    public static DownLoadManger getInstance() {
        if (instance == null) {
            instance = new DownLoadManger();
        }
        if(db==null){
            DbManager.DaoConfig daoConfig = DbUtils.getDaoConfig();
            db = x.getDb(daoConfig);
        }
        return instance;
    }

    public void  down(final GameInfo app) {
        if(!TextUtils.isEmpty(app.getAndroid())){
            Log.e("下载地址",app.getAndroid());
        }else{
            ToastUtil.showToast("请上传正确的下载链接！");
            return;
        }
        RequestParams entity = new RequestParams(app.getAndroid());
//        RequestParams entity = new RequestParams("https://mobile.hupu.com/download/games/?os=android&r=kanqiu");
        entity.setAutoResume(true);                     //是否断点下载
        entity.setAutoRename(false);                    //下载完成后是否自动重命名
        entity.setCancelFast(true);                     //快速取消
        entity.setExecutor(exec);                       //线程池
        entity.setSaveFilePath(getApkFile(String.valueOf(app.getId())).getAbsolutePath());//文件保存路径
        Log.e("文件保存路径",getApkFile(String.valueOf(app.getId())).getAbsolutePath());
        Callback.Cancelable cancelable = x.http().get(entity, new Callback.ProgressCallback<File>() {

            @Override
            public void onCancelled(CancelledException arg0) {
                Log.e("下载取消",arg0.toString());
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

                app.setBtn_tatus(DOWNLOAD_FAILED);
                app.setLishi(1);
                app.setZhong( 0);
                notifyDownloadStateChanged(app);
                Log.e("下载失败原因",arg0.toString());
                try {
                    db.saveOrUpdate(app);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinished() {
                System.out.println("下载完成++++++++");
            }

            @Override
            public void onSuccess(File arg0) {
                app.setLishi(2);
                app.setZhong(3);
                app.setXia(2);
                app.setBtn_tatus(DOWNLOAD_SUCCESS);
                System.out.println("下载成功++++++++" + arg0);
                try {
                    db.saveOrUpdate(app);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notifyDownloadStateChanged(app);
            }

            @Override
            public void onLoading(long arg0, long arg1, boolean arg2) {

                app.setProgress((double) arg1);
                app.setZsize((double) arg0);
                app.setZhong(2);
                app.setXia(2);
                app.setSpent(Utils.getSpent(arg1));
                app.setBtn_tatus(DOWNLOAD_LOADING);
                notifyDownloadStateChanged(app);
                try {
                    db.saveOrUpdate(app);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStarted() {
                app.setZhong(2) ;
                app.setLishi(0) ;
                app.setXia(2);
                try {
                    db.saveOrUpdate(app);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onWaiting() {
                //等待
                app.setBtn_tatus(DOWNLOAD_WAITING);

                app.setZhong(2);
                app.setXia(2) ;
                notifyDownloadStateChanged(app);
                try {
                    db.saveOrUpdate(app);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
        DownloadInfo downloadInfo = new DownloadInfo();
        // 保存记录下载的信息
        downloadInfo.setCancelable(cancelable);
        mDownloadInfos.put(String.valueOf(app.getId()), downloadInfo);
    }

    /**
     * @return
     * 已经下载 或者有本地下载历史
     */
    public List<GameInfo> getDownLoadingGames() {
        DbManager db = DbUtils.getDB();
        try {
            XiaAppList=db.selector(GameInfo.class).where("btn_tatus","==",1).or("btn_tatus","==",2).or("btn_tatus","==",3).or("btn_tatus","==",4).or("btn_tatus","==",6).findAll();
            return XiaAppList;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("查找下载中游戏异常",e.toString());
            return null;
        }



    }

    /**
     * @return
     * 已经下载 或者有本地下载历史
     */
    public List<GameInfo> getOnlyDownLoadingGames() {
        DbManager db = DbUtils.getDB();
        try {
            XiaAppList=db.selector(GameInfo.class).where("btn_tatus","==",2).findAll();
            return XiaAppList;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("查找下载中游戏异常",e.toString());
            return null;
        }



    }

    /**
     * 文件保存路径
     * @param packageName
     * @return
     */
    public File getApkFile(String packageName) {
        String dir = FileUtil.getDownloadDir().getAbsolutePath();
        return new File(dir, packageName + ".apk");
    }

    /**
     * 暂停下载
     * @param bean
     */
    public void pause(GameInfo bean) {
        DownloadInfo info = mDownloadInfos.get(String.valueOf(bean.getId()));
        if (info!=null) { info.getCancelable().cancel();
            }

        // 改变状态
        bean.setBtn_tatus(DOWNLOAD_PAUSE) ;
        notifyDownloadStateChanged(bean);
        try {
            db.saveOrUpdate(bean);
        } catch (DbException e) {
            e.printStackTrace();
            Log.e("暂停跑出异常",e.toString());
        }
    }

    /**
     * 安装
     * @param bean
     */
    public void install(GameInfo bean) {

        File apkFile = getApkFile(String.valueOf(bean.getId()));
        try {
            if (!apkFile.exists()) {
                ToastUtil.showToast("安装包不存在或已删除！");
                bean.setBtn_tatus(DOWNLOAD_NOT);
                bean.setZhong(0);
                bean.setLishi(0);
                bean.setXia(0);
                notifyDownloadStateChanged(bean);
                db.saveOrUpdate(bean);
            } else {
                Utils.installApp(x.app(), apkFile);
                String dir = FileUtil.getDownloadDir().getAbsolutePath();
                String getPackageName = Utils.getPackageName(x.app(), dir + "/" + bean.getId() + ".apk");
                // 改变状态
                bean.setPackage_name(getPackageName);
                bean.setBtn_tatus(DOWNLOAD_INSTALL) ;
                notifyDownloadStateChanged(bean);
                db.saveOrUpdate(bean);
//                delete(bean);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 打开
     * @param bean
     */
    public void open(GameInfo bean) {
        try {
            Log.e("打开的包名：",bean.getPackage_name());
            Utils.openApp(x.app(),bean);
        }catch (Exception e){
            e.printStackTrace();
            try {
                bean.setBtn_tatus( DOWNLOAD_NOT);
                notifyDownloadStateChanged(bean);
                db.saveOrUpdate(bean);
                Toast.makeText(x.app(), "您已经卸载,请重新下载并安装！", Toast.LENGTH_SHORT).show();
                Log.e("暂无该包名游戏", "无法启动");
            } catch (DbException e1) {
                e1.printStackTrace();
            }
        }
    }



    /**
     * 是否安装过了
     * @param bean
     */
    public void isInstall(GameInfo bean){
        String dir = FileUtil.getDownloadDir().getAbsolutePath();
        File apkFile = getApkFile(String.valueOf(bean.getId()));
        if (Utils.isInstall(x.app(),bean.getPackage_name())) {
            Log.e("下载且安装了",bean.getPackage_name());
            // 改变状态
            bean.setBtn_tatus(DOWNLOAD_INSTALL);
            try {
                db.saveOrUpdate(bean);
//                delete(bean);
            } catch (DbException e) {
                e.printStackTrace();
            }
            notifyDownloadStateChanged(bean);
            return;
        } else{
            if(apkFile.exists()){
                Log.e("下载但未安装",bean.getPackage_name());
                // 改变状态
                bean.setBtn_tatus(DOWNLOAD_SUCCESS) ;
                notifyDownloadStateChanged(bean);
                try {
                    db.saveOrUpdate(bean);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除
     * @param bean
     */
    public void delete(GameInfo bean){
        if(bean.getBtn_tatus()==2){
            pause(bean);
        }
        try {
            File apkFile = getApkFile(String.valueOf(bean.getId()));
            File apkFile1 = getApkFile1(String.valueOf(bean.getId()));
            if(apkFile.exists()){
                apkFile.delete();
            }
            if(apkFile1.exists()){
                apkFile1.delete();
            }
            db.delete(bean);
        } catch (Exception e) {
            e.printStackTrace();
           ToastUtil.showToast("删除出错");
        }
    }

    /**
     * 删除所有apk
     */
    public void deleteAllApk(){
       DeleteUtil.delete(FileUtil.getDownloadDir().getAbsolutePath(),false,".apk");
    }


    private File getApkFile1(String packageName) {
        String dir = FileUtil.getDownloadDir().getAbsolutePath();
        return new File(dir, packageName + ".apk");
    }

    /**
     * 添加观察者
     *
     * @param observer
     */
    public void addObserver(DownloadObserver observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!mObservers.contains(observer)) mObservers.add(observer);
        }
    }

    /**
     * 删除观察者
     *
     * @param observer
     */
    public synchronized void deleteObserver(DownloadObserver observer) {
        mObservers.remove(observer);
    }

    /**
     * 通知观察者数据改变        //按钮更新在这里修改
     * @param
     */
    public void notifyDownloadStateChanged(GameInfo bean) {
        ListIterator<DownloadObserver> iterator = mObservers.listIterator();
        while (iterator.hasNext()) {
            DownloadObserver observer = iterator.next();
            observer.onDownloadStateChanged(this, bean);
        }
    }
}
