package com.wag.gamebox.app;

import android.app.Application;
import android.content.Context;

import com.wag.gamebox.api.Constant;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import org.xutils.x;

import static com.wag.gamebox.api.Constant.QQ_APP_ID;


public class WagGameBoxApp extends Application{

    private static Context application;
    public static IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        application=this;
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, false);
        api.registerApp(Constant.WX_APP_ID);
    }

    public static Context getApplication(){
        return application;
    }
}
