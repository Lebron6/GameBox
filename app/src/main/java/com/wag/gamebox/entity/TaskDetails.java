package com.wag.gamebox.entity;

/**
 * Created by James on 2019/9/17.
 */
public class TaskDetails {
    /**
     * code : 200
     * msg : 修改成功
     * time : 1568719074
     * data : {"id":9,"name":"sdk测试时长","start_time":"2019-09-11","end_time":"2019-09-13","type":2,"game_id":222,"duration":"1","explain":"时长","status":1}
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
         * id : 9
         * name : sdk测试时长
         * start_time : 2019-09-11
         * end_time : 2019-09-13
         * type : 2
         * game_id : 222
         * duration : 1
         * explain : 时长
         * status : 1
         */

        private int id;
        private String name;
        private String start_time;
        private String end_time;
        private int type;
        private int game_id;
        private String duration;
        private String explain;
        private int status;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getGame_id() {
            return game_id;
        }

        public void setGame_id(int game_id) {
            this.game_id = game_id;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
