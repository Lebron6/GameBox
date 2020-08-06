package com.wag.gamebox.entity;

/**
 * Created by James on 2018/10/17.
 */
public class LoginResult {
    /**
     * code : 200
     * msg : 登录成功
     * time : 1539767122
     * data : {"token":"0a0d5ec6dfaea563773e5745b3a1767273aa2238930241daf391a82518490d65","user_login":"13921770204"}
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
         * token : 0a0d5ec6dfaea563773e5745b3a1767273aa2238930241daf391a82518490d65
         * user_login : 13921770204
         */

        private String token;
        private String user_login;
        private int login_type;

        public int getLogin_type() {
            return login_type;
        }

        public void setLogin_type(int login_type) {
            this.login_type = login_type;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser_login() {
            return user_login;
        }

        public void setUser_login(String user_login) {
            this.user_login = user_login;
        }
    }
}
