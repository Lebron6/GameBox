package com.wag.gamebox.entity;

/**
 * Created by James on 2018/10/17.
 */
public class GiftDetails {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1540273823
     * data : {"deadline":"2018年12月1日 8:00 - 2018年12月1日 8:00","name":"贵族礼包","content":"高级守卫令牌，金条(大)，斧子，副本卷轴，玛雅卷轴","explain":"角色达到60级-奖励大厅-激活码对换-邮件领取","game_name":"王者传奇","logo_img":"/game/testimg/3c76aacec67c183de0d813149ce5357f.png","cdkey":"fsdfefergrgerhteh4","status":2}
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
         * deadline : 2018年12月1日 8:00 - 2018年12月1日 8:00
         * name : 贵族礼包
         * content : 高级守卫令牌，金条(大)，斧子，副本卷轴，玛雅卷轴
         * explain : 角色达到60级-奖励大厅-激活码对换-邮件领取
         * game_name : 王者传奇
         * logo_img : /game/testimg/3c76aacec67c183de0d813149ce5357f.png
         * cdkey : fsdfefergrgerhteh4
         * status : 2
         */

        private String deadline;
        private String name;
        private String content;
        private String explain;
        private String game_name;
        private String logo_img;
        private String cdkey;
        private int status;

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
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

        public String getCdkey() {
            return cdkey;
        }

        public void setCdkey(String cdkey) {
            this.cdkey = cdkey;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}