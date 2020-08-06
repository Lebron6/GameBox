package com.wag.gamebox.tools;

import android.content.Context;

import com.wag.gamebox.app.WagGameBoxApp;

/**
 * 具体实现sp 
 * Created by James on 2016/10/30.
 */  
public class PreferenceUtils extends BasePreference {  
    private static PreferenceUtils preferenceUtils;

    /** 
     * 需要增加key就在这里新建 
     */  
    //用户名的key  
    private String USER_NAME = "user_name";

    //用户token的key
    private String USER_TOKEN = "user_token";

    //用户头像的key
    private String USER_HEADIMG = "user_headimg";

    //用户ID的key
    private String USER_ID = "user_id";

    //是否登录的key
    private String LOGIN_STATUS = "login_status";

    //记住账号
    private String REMEMBERACCOUNT="remember_account";

    //记住密码
    private String REMEMBERPASSWORD="remember_password";

    //登录方式
    private String LOGIN_TYPE="login_type";     //1 手机号  2三方

    private String THIRD_LOGIN_TYPE="third_login_type";//第三方登录方式

    private String OPEN_ID="open_id";       //第三方登录openId

    public void setOpenId(String openId){
        setString(OPEN_ID,openId);
    }

    public String getOpenId(){
        return getString(OPEN_ID);
    }

    public void setThirdLoginType(String thirdLoginType){
        setString(THIRD_LOGIN_TYPE,thirdLoginType);
    }

    public String getThirdLoginType(){
        return getString(THIRD_LOGIN_TYPE);
    }


    public void setLoginType(int loginType){
        setInt(LOGIN_TYPE,loginType);
    }

    public int getLoginType(){
        return getInt(LOGIN_TYPE);
    }

    public String getRemeberPassword() {
        return getString(REMEMBERPASSWORD);
    }

    public void setRemeberPassword(String password){
        setString(REMEMBERPASSWORD,password);
    }

    public String getRemeberAccount() {
        return getString(REMEMBERACCOUNT);
    }

    public void setRemeberAccount(String account){
        setString(REMEMBERACCOUNT,account);
    }



    public void loginOut(){
        userLoginOut();
    }
  
    public void setLoginStatus(boolean isFirst) {
        setBoolean(LOGIN_STATUS,isFirst);
    }

    public boolean getLoginStatus() {
        return getBoolean(LOGIN_STATUS);
    }

    public void setUserID(int id){
        setInt(USER_ID,id);
    }

    public int getUserId(){
        return getInt(USER_ID);
    }

    public String getUserToken() {
        return getString(USER_TOKEN);
    }

    public void setUserToken(String token){
        setString(USER_TOKEN,token);
    }

    public void setUserHeadimg(String userHeadimg){
        setString(USER_HEADIMG,userHeadimg);
    }

    public String getUserHeadimg() {
        return getString(USER_HEADIMG);
    }

    public void setUserName(String name) {
        setString(USER_NAME, name);
    }  
  
    public String getUserName() {
        return getString(USER_NAME);  
    }



    private PreferenceUtils(Context context) {
        super(context);
    }
    /**
     * 这里我通过自定义的Application来获取Context对象，所以在获取preferenceUtils时不需要传入Context。
     * @return
     */
    public synchronized static PreferenceUtils getInstance() {
        if (null == preferenceUtils) {
            preferenceUtils = new PreferenceUtils(WagGameBoxApp.getApplication());
        }
        return preferenceUtils;
    }
}