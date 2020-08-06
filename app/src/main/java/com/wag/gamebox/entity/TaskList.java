package com.wag.gamebox.entity;

import java.util.List;

/**
 * Created by James on 2019/8/20.
 */
public class TaskList {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1566299040
     * data : [{"id":1,"name":"任务名称","start_time":"开始时间","end_time":"结束时间","status":"1待完成  2待领取 3已领取"}]
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
         * name : 任务名称
         * start_time : 开始时间
         * end_time : 结束时间
         * status : 1待完成  2待领取 3已领取
         */

        private int id;
        private String name;
        private String start_time;
        private String end_time;
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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


    }
}
