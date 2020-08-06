package com.wag.gamebox.entity;

import java.util.List;

/**
 * Created by James on 2018/10/26.
 */
public class MyPTB {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1538277603
     * data : {"user":{"wallet":"平台币"},"list":[{"coin":"数量","create_time":"时间","info":"充值"}]}
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
         * user : {"wallet":"平台币"}
         * list : [{"coin":"数量","create_time":"时间","info":"充值"}]
         */

        private UserBean user;
        private List<ListBean> list;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class UserBean {
            /**
             * wallet : 平台币
             */

            private String wallet;

            public String getWallet() {
                return wallet;
            }

            public void setWallet(String wallet) {
                this.wallet = wallet;
            }
        }

        public static class ListBean {
            /**
             * coin : 数量
             * create_time : 时间
             * info : 充值
             */

            private String coin;
            private String create_time;
            private String info;

            public String getCoin() {
                return coin;
            }

            public void setCoin(String coin) {
                this.coin = coin;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }
        }
    }
}
