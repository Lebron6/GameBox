package com.wag.gamebox.entity;

import java.util.List;

/**
 * Created by James on 2018/10/17.
 */
public class InfoBean {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1539754147
     * data : [{"id":7,"title":"惊喜开服活动，好礼送不停\r\n惊喜开服活动，好礼送不停\r\n王者传奇 ","content":"","create_time":"2018-09-03","cover":"/game/testimg/6aa1093a500f7c8a80470b970f1595ff.jpg","url":"http:://www.baidu.com"},{"id":6,"title":"惊喜开服活动，好礼送不停\r\n惊喜开服活动，好礼送不停\r\n","content":"","create_time":"2018-09-04","cover":"/game/testimg/6aa1093a500f7c8a80470b970f1595ff.jpg","url":"http:://www.baidu.com"},{"id":5,"title":"《古龙群侠传2》论剑决系统详解：如何成为天下第一","content":"","create_time":"2018-09-29","cover":"/game/testimg/6aa1093a500f7c8a80470b970f1595ff.jpg","url":"http:://www.baidu.com"},{"id":4,"title":"《加勒比海盗：启航》教你如何快速升级","content":"","create_time":"2018-09-25","cover":"/game/testimg/6aa1093a500f7c8a80470b970f1595ff.jpg","url":"http:://www.baidu.com"}]
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
         * id : 7
         * title : 惊喜开服活动，好礼送不停
         惊喜开服活动，好礼送不停
         王者传奇
         * content :
         * create_time : 2018-09-03
         * cover : /game/testimg/6aa1093a500f7c8a80470b970f1595ff.jpg
         * url : http:://www.baidu.com
         */

        private int id;
        private String title;
        private String content;
        private String create_time;
        private String cover;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
