package com.wag.gamebox.entity;

import java.util.List;

/**
 * Created by James on 2018/10/22.
 */
public class GameTypeBean {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1540195456
     * data : [{"id":1,"type_name":"传奇类"},{"id":2,"type_name":"三国slg"},{"id":3,"type_name":"魔幻rpg"},{"id":4,"type_name":"跳舞类"},{"id":5,"type_name":"策略卡牌"},{"id":6,"type_name":"战争策略"},{"id":7,"type_name":"RPG卡牌"},{"id":8,"type_name":"策略型"},{"id":9,"type_name":"仙侠类"},{"id":10,"type_name":"魔幻类"}]
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
         * id : 1
         * type_name : 传奇类
         */

        private int id;
        private String type_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }
}
