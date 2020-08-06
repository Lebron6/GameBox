package com.wag.gamebox.entity;

import java.util.List;

/**
 * Created by James on 2019/6/5.
 */
public class CommentDetails {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1559028832
     * data : {"id":"评论id","time":"时间","content":"评论内容","user_name":"用户名","head_img":"头像","game_id":"游戏id","list":[{"content":"评论内容","user_name":"用户名","back_user_name":"回复后面的 用户名","type":"1代表没有回复后面的名字   2代表有","user_id":"用户id","head_img":"头像","time":"时间"}]}
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
         * id : 评论id
         * time : 时间
         * content : 评论内容
         * user_name : 用户名
         * head_img : 头像
         * game_id : 游戏id
         * list : [{"content":"评论内容","user_name":"用户名","back_user_name":"回复后面的 用户名","type":"1代表没有回复后面的名字   2代表有","user_id":"用户id","head_img":"头像","time":"时间"}]
         */

        private String id;
        private String time;
        private String content;
        private String user_name;
        private String head_img;
        private String game_id;
        private List<ListBean> list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getGame_id() {
            return game_id;
        }

        public void setGame_id(String game_id) {
            this.game_id = game_id;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * content : 评论内容
             * user_name : 用户名
             * back_user_name : 回复后面的 用户名
             * type : 1代表没有回复后面的名字   2代表有
             * user_id : 用户id
             * head_img : 头像
             * time : 时间
             */

            private String content;
            private String user_name;
            private String back_user_name;
            private String type;
            private String user_id;
            private String head_img;
            private String time;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getBack_user_name() {
                return back_user_name;
            }

            public void setBack_user_name(String back_user_name) {
                this.back_user_name = back_user_name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
