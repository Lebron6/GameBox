package com.wag.gamebox.entity;

/**
 * Created by James on 2018/10/25.
 */
public class VersionInfo {

    /**
     * code : 300
     * msg : 新版本
     * time : 1540455582
     * data : {"android_info":null,"android_url":null}
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
         * android_info : null
         * android_url : null
         */

        private String android_info;
        private String android_url;

        public String getAndroid_info() {
            return android_info;
        }

        public void setAndroid_info(String android_info) {
            this.android_info = android_info;
        }

        public String getAndroid_url() {
            return android_url;
        }

        public void setAndroid_url(String android_url) {
            this.android_url = android_url;
        }
    }
}
