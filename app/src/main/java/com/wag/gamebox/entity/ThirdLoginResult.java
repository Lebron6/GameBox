package com.wag.gamebox.entity;

/**
 * Created by James on 2018/10/31.
 */
public class ThirdLoginResult {
    /**
     * code : 200
     * msg : 登录成功
     * time : 1540531940
     * data : {"token":"token","third_type":"第三方登录类型","user_login":"手机号","openid":"第三方id","login_type":1}
     */

    private String code;
    private String msg;
    private String time;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : token
         * third_type : 第三方登录类型
         * user_login : 手机号
         * openid : 第三方id
         * login_type : 1
         */

        private String token;
        private String third_type;
        private String user_login;
        private String openid;
        private int login_type;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getThird_type() {
            return third_type;
        }

        public void setThird_type(String third_type) {
            this.third_type = third_type;
        }

        public String getUser_login() {
            return user_login;
        }

        public void setUser_login(String user_login) {
            this.user_login = user_login;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public int getLogin_type() {
            return login_type;
        }

        public void setLogin_type(int login_type) {
            this.login_type = login_type;
        }
    }
}
