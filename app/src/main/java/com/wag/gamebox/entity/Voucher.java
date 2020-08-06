package com.wag.gamebox.entity;

import java.util.List;

/**
 * Created by James on 2019/8/20.
 */
public class Voucher {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1566303923
     * data : [{"name":"登录游戏盒子","coupon":"30","explain":"满30可以用","start_time":"2019-08-02","end_time":"2019-09-09","id":1}]
     */

    private String code;
    private String msg;
    private String time;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 登录游戏盒子
         * coupon : 30
         * explain : 满30可以用
         * start_time : 2019-08-02
         * end_time : 2019-09-09
         * id : 1
         */

        private String name;
        private String coupon;
        private String explain;
        private String start_time;
        private String end_time;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
