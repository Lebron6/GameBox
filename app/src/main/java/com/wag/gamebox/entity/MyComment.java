package com.wag.gamebox.entity;

import java.util.List;

/**
 * Created by James on 2019/6/6.
 */
public class MyComment {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1559185074
     * data : [{"id":"评论id","content":"评论内容","game_name":"游戏名称","logo_img":"游戏logo","game_id":"游戏id","user_name":"用户名","head_img":"头像","back":"回复条数"}]
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
         * id : 评论id
         * content : 评论内容
         * game_name : 游戏名称
         * logo_img : 游戏logo
         * game_id : 游戏id
         * user_name : 用户名
         * head_img : 头像
         * back : 回复条数
         */

        private int id;
        private String content;
        private String game_name;
        private String logo_img;
        private int game_id;
        private String user_name;
        private String head_img;
        private int back;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public int getGame_id() {
            return game_id;
        }

        public void setGame_id(int game_id) {
            this.game_id = game_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public int getBack() {
            return back;
        }

        public void setBack(int back) {
            this.back = back;
        }
    }
}
