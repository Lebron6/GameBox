package com.wag.gamebox.entity;

/**
 * Created by James on 2018/10/17.
 */
public class RegisterResult {
    /**
     * code : 200
     * msg : 注册成功
     * time : 1539764999
     * data : {"token":"7bec1d9130abb6f0dbfa9246598d40d6c7a15bad00e4b9cef6d31c23a460ca78","user_login":"13921770204"}
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
         * token : 7bec1d9130abb6f0dbfa9246598d40d6c7a15bad00e4b9cef6d31c23a460ca78
         * user_login : 13921770204
         */

        private String token;
        private String user_login;

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
