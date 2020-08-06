package com.wag.gamebox.entity;

/**
 * Created by James on 2019/5/6.
 */
public class VipOpen {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1556592550
     * data : {"vip":"会员等级","discount":"折扣数   0-9折","open_vip":"折扣开关   1开启   2关闭"}
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
         * vip : 会员等级
         * discount : 折扣数   0-9折
         * open_vip : 折扣开关   1开启   2关闭
         */

        private String vip;
        private String discount;
        private String open_vip;

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getOpen_vip() {
            return open_vip;
        }

        public void setOpen_vip(String open_vip) {
            this.open_vip = open_vip;
        }
    }
}
