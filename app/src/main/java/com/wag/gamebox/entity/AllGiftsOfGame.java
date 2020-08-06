package com.wag.gamebox.entity;

import java.util.List;

/**
 * Created by James on 2018/10/17.
 */
public class AllGiftsOfGame {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1539930509
     * data : {"game_info":{"id":2,"game_name":"仙妖劫","logo_img":"/game/testimg/3c76aacec67c183de0d813149ce5357f.png","game_size":"175M","type_name":"三国slg","ios":"http://192.168.1.201/11.ipa","android":"https://mobile.hupu.com/download/games/?os=android&r=kanqiu","kac_count":1},"kac_list":[{"kac_id":2,"content":"1.5倍圣灵泉x5、精炼活血剂x2、高级能量药剂x2、复活卷x5、超级大喇叭x5玩家等级达到17级可点击右上角的福利大厅，选择激活码，输入进行兑换","name":"平台独家礼包","status":2,"cdkey":"abcdefg3"}]}
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
         * game_info : {"id":2,"game_name":"仙妖劫","logo_img":"/game/testimg/3c76aacec67c183de0d813149ce5357f.png","game_size":"175M","type_name":"三国slg","ios":"http://192.168.1.201/11.ipa","android":"https://mobile.hupu.com/download/games/?os=android&r=kanqiu","kac_count":1}
         * kac_list : [{"kac_id":2,"content":"1.5倍圣灵泉x5、精炼活血剂x2、高级能量药剂x2、复活卷x5、超级大喇叭x5玩家等级达到17级可点击右上角的福利大厅，选择激活码，输入进行兑换","name":"平台独家礼包","status":2,"cdkey":"abcdefg3"}]
         */

        private GameInfoBean game_info;
        private List<KacListBean> kac_list;

        public GameInfoBean getGame_info() {
            return game_info;
        }

        public void setGame_info(GameInfoBean game_info) {
            this.game_info = game_info;
        }

        public List<KacListBean> getKac_list() {
            return kac_list;
        }

        public void setKac_list(List<KacListBean> kac_list) {
            this.kac_list = kac_list;
        }

        public static class GameInfoBean {
            /**
             * id : 2
             * game_name : 仙妖劫
             * logo_img : /game/testimg/3c76aacec67c183de0d813149ce5357f.png
             * game_size : 175M
             * type_name : 三国slg
             * ios : http://192.168.1.201/11.ipa
             * android : https://mobile.hupu.com/download/games/?os=android&r=kanqiu
             * kac_count : 1
             */

            private int id;
            private String game_name;
            private String logo_img;
            private String game_size;
            private String type_name;
            private String ios;
            private String android;
            private int kac_count;




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

            public String getGame_size() {
                return game_size;
            }

            public void setGame_size(String game_size) {
                this.game_size = game_size;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getIos() {
                return ios;
            }

            public void setIos(String ios) {
                this.ios = ios;
            }

            public String getAndroid() {
                return android;
            }

            public void setAndroid(String android) {
                this.android = android;
            }

            public int getKac_count() {
                return kac_count;
            }

            public void setKac_count(int kac_count) {
                this.kac_count = kac_count;
            }
        }

        public static class KacListBean {
            /**
             * kac_id : 2
             * content : 1.5倍圣灵泉x5、精炼活血剂x2、高级能量药剂x2、复活卷x5、超级大喇叭x5玩家等级达到17级可点击右上角的福利大厅，选择激活码，输入进行兑换
             * name : 平台独家礼包
             * status : 2
             * cdkey : abcdefg3
             */

            private int kac_id;
            private String content;
            private String name;
            private int status;
            private String cdkey;
            private int id;
            private int residue;

            public int getResidue() {
                return residue;
            }

            public void setResidue(int residue) {
                this.residue = residue;
            }
            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getKac_id() {
                return kac_id;
            }

            public void setKac_id(int kac_id) {
                this.kac_id = kac_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCdkey() {
                return cdkey;
            }

            public void setCdkey(String cdkey) {
                this.cdkey = cdkey;
            }
        }
    }
}
