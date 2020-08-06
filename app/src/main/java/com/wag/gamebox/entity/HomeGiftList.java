package com.wag.gamebox.entity;

import java.util.List;

/**
 * Created by James on 2018/10/16.
 */
public class HomeGiftList {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1537931222
     * data : [{"game_id":"游戏id","game_name":"游戏名","logo_img":"logo","kac_name":"礼包名称","kac_count":"礼包总数"}]
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
         * game_id : 游戏id
         * game_name : 游戏名
         * logo_img : logo
         * kac_name : 礼包名称
         * kac_count : 礼包总数
         */

        private int game_id;
        private String game_name;
        private String logo_img;
        private String kac_name;
        private String kac_count;

        public int getGame_id() {
            return game_id;
        }

        public void setGame_id(int game_id) {
            this.game_id = game_id;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public String getLogo_img() {
            return logo_img;
        }

        public void setLogo_img(String logo_img) {
            this.logo_img = logo_img;
        }

        public String getKac_name() {
            return kac_name;
        }

        public void setKac_name(String kac_name) {
            this.kac_name = kac_name;
        }

        public String getKac_count() {
            return kac_count;
        }

        public void setKac_count(String kac_count) {
            this.kac_count = kac_count;
        }
    }
}
