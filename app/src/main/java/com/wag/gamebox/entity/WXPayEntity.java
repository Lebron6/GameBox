package com.wag.gamebox.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 2018/11/2.
 */
public class WXPayEntity {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1541143053
     * data : {"appid":"wxe18750489849b561","noncestr":"iezx6m9yhp1nfle1p4dzab4y8aasmp8k","package":"Sign=WXPay","partnerid":"1517702931","prepayid":"wx02151732179146175ec6d6cd2702186105","timestamp":1541143053,"sign":"33B830D1AAE6BE6BDD190B36B763B96D"}
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
         * appid : wxe18750489849b561
         * noncestr : iezx6m9yhp1nfle1p4dzab4y8aasmp8k
         * package : Sign=WXPay
         * partnerid : 1517702931
         * prepayid : wx02151732179146175ec6d6cd2702186105
         * timestamp : 1541143053
         * sign : 33B830D1AAE6BE6BDD190B36B763B96D
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
