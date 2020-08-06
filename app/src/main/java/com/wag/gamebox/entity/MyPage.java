package com.wag.gamebox.entity;

import java.io.Serializable;

/**
 * Created by James on 2018/10/25.
 */
public class MyPage {


    /**
     * code : 200
     * msg : 上传成功
     * time :
     * data : {"head_img":"头像","user_login":"账号","user_name":"用户名","binding":"0未绑定 1绑定","auth":"实名认证  0未认证  1认证"}
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

    public static class DataBean implements Serializable {
        @Override
        public String toString() {
            return "DataBean{" +
                    "head_img='" + head_img + '\'' +
                    ", user_login='" + user_login + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", binding=" + binding +
                    ", auth=" + auth +
                    ", wallet='" + wallet + '\'' +
                    ", amount='" + amount + '\'' +
                    ", vip='" + vip + '\'' +
                    ", min='" + min + '\'' +
                    ", service_tel='" + service_tel + '\'' +
                    ", coupon=" + coupon +
                    ", task=" + task +
                    '}';
        }

        /**
         * head_img : 头像
         * user_login : 账号
         * user_name : 用户名
         * binding : 0未绑定 1绑定
         * auth : 实名认证  0未认证  1认证
         */

        private String head_img;
        private String user_login;
        private String user_name;
        private int binding;
        private int auth;
        private String wallet;
        private String amount;
        private String vip;
        private String min;
        private String service_tel;
        private int coupon;
        private int task;

        public int getCoupon() {
            return coupon;
        }

        public void setCoupon(int coupon) {
            this.coupon = coupon;
        }

        public int getTask() {
            return task;
        }

        public void setTask(int task) {
            this.task = task;
        }

        public String getService_tel() {
            return service_tel;
        }

        public void setService_tel(String service_tel) {
            this.service_tel = service_tel;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getWallet() {
            return wallet;
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getUser_login() {
            return user_login;
        }

        public void setUser_login(String user_login) {
            this.user_login = user_login;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getBinding() {
            return binding;
        }

        public void setBinding(int binding) {
            this.binding = binding;
        }

        public int getAuth() {
            return auth;
        }

        public void setAuth(int auth) {
            this.auth = auth;
        }
    }
}
