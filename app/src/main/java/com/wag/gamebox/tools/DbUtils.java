package com.wag.gamebox.tools;

import org.xutils.DbManager;
import org.xutils.x;

public class DbUtils {

    static DbManager.DaoConfig daoConfig;
    public static DbManager.DaoConfig getDaoConfig(){
        //File file=new File(Environment.getExternalStorageDirectory().getPath());
        if(daoConfig==null){
            daoConfig=new DbManager.DaoConfig()
                    .setDbName("dlgamebox.db")                 //设置数据库名称
                    .setDbDir(FileUtil.getDownloadDir())                             //设置数据库存放路径
                    .setDbVersion(1)                            //设置数据库版本
                    .setAllowTransaction(true)                  //设置全部开启事务
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        }
                    });
        }
        return daoConfig;
    }

    public static DbManager getDB(){
        return x.getDb(getDaoConfig());
    }
}
