package com.wag.gamebox.api;

/**
 * Created by James on 2018/10/11.
 */
public class Constant {

    /**
     * 请求成功
     */
    public static final int REQUEST_SUCCESS=200;

    /**
     * 请求失败
     */
    public static final int REQUEST_FAIL=500;

    /**
     * QQAPPID
     */
    public static final String QQ_APP_ID="101770858";
//    public static final String QQ_APP_ID="101508367";

    /**
     * 微信APPID  //可能有问题
     */
    public static final String WX_APP_ID="wxfda38e9ac709e3fb";
    /**
     * 微信APPSECRET
     */
    public static final String WX_SECRET="54f2abfea1e7ddea321eca84773426d5";

    /**
     * 登录成功请求获取微信ACCESS_TOKEN
     */
    public static final int REQUEST_ACCESS_TOKEN=1;

    /**
     * 登录成功请求获取微信用户信息
     */
    public static final int REQUEST_WX_USER_INFO=2;

    /**
     * 微博APPKEY
     */
    public static final String WEIBO_APP_KEY="2714508848";

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     */
    public static final String REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";

    /**
     * WeiboSDKDemo 应用对应的权限，第三方开发者一般不需要这么多，可直接设置成空即可。
     * 详情请查看 Demo 中对应的注释。
     */
    public static final String SCOPE =
            "";
}
