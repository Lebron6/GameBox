package com.wag.gamebox.entity;

import java.util.List;

/**
 * Created by James on 2018/10/19.
 */
public class MyGiftData {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1539926804
     * data : [{"id":2,"game_name":"仙妖劫","logo_img":"/game/testimg/3c76aacec67c183de0d813149ce5357f.png","list":"平台独家礼包","count":1}]
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
         * id : 2
         * game_name : 仙妖劫
         * logo_img : /game/testimg/3c76aacec67c183de0d813149ce5357f.png
         * list : 平台独家礼包
         * count : 1
         */

        private int id;
        private String game_name;
        private String logo_img;
        private String list;
        private int count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getList() {
            return list;
        }

        public void setList(String list) {
            this.list = list;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
