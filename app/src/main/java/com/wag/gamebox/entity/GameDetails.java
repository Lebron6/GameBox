package com.wag.gamebox.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by James on 2018/10/16.
 */
public class GameDetails  implements Serializable{
    /**
     * code : 200
     * msg : 请求成功
     * time : 1539678322
     * data : {"game_info":{"game_name":"王者传奇","logo_img":"/game/testimg/3c76aacec67c183de0d813149ce5357f.png","game_size":"170M","type_name":"传奇类","down_count":"100万次","ios":"http://192.168.1.201/11.ipa","android":"https://mobile.hupu.com/download/games/?os=android&r=kanqiu"},"kac_list":[{"id":1,"name":"贵族礼包","content":"高级守卫令牌，金条(大)，斧子，副本卷轴，玛雅卷轴","residue":4996,"status":1}],"game_details":{"feature_img":[{"url":"/game/testimg/63365ce734a9c0cd0554ce23c253fb85.jpg"},{"url":"/game/testimg/63365ce734a9c0cd0554ce23c253fb85.jpg"}],"game_details":"经典1.76传奇动作手游《王者传奇》震撼来袭！游戏继承\u201c传奇\u201d的核心玩法，延续了传奇世界的经典设定，完美复刻顶级蓝光画质。炫酷套装，百人同屏PK，重温经典传奇。"}}
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

    public static class DataBean  implements Serializable{
        /**
         * game_info : {"game_name":"王者传奇","logo_img":"/game/testimg/3c76aacec67c183de0d813149ce5357f.png","game_size":"170M","type_name":"传奇类","down_count":"100万次","ios":"http://192.168.1.201/11.ipa","android":"https://mobile.hupu.com/download/games/?os=android&r=kanqiu"}
         * kac_list : [{"id":1,"name":"贵族礼包","content":"高级守卫令牌，金条(大)，斧子，副本卷轴，玛雅卷轴","residue":4996,"status":1}]
         * game_details : {"feature_img":[{"url":"/game/testimg/63365ce734a9c0cd0554ce23c253fb85.jpg"},{"url":"/game/testimg/63365ce734a9c0cd0554ce23c253fb85.jpg"}],"game_details":"经典1.76传奇动作手游《王者传奇》震撼来袭！游戏继承\u201c传奇\u201d的核心玩法，延续了传奇世界的经典设定，完美复刻顶级蓝光画质。炫酷套装，百人同屏PK，重温经典传奇。"}
         */

        private GameInfo game_info;
        private GameDetailsBean game_details;
        private List<KacListBean> kac_list;
        private List<CommentsBean> comments;

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }
        public GameInfo getGame_info() {
            return game_info;
        }

        public void setGame_info(GameInfo game_info) {
            this.game_info = game_info;
        }

        public GameDetailsBean getGame_details() {
            return game_details;
        }

        public void setGame_details(GameDetailsBean game_details) {
            this.game_details = game_details;
        }

        public List<KacListBean> getKac_list() {
            return kac_list;
        }

        public void setKac_list(List<KacListBean> kac_list) {
            this.kac_list = kac_list;
        }

        public static class GameInfoBean  implements Serializable{
            /**
             * game_name : 王者传奇
             * logo_img : /game/testimg/3c76aacec67c183de0d813149ce5357f.png
             * game_size : 170M
             * type_name : 传奇类
             * down_count : 100万次
             * ios : http://192.168.1.201/11.ipa
             * android : https://mobile.hupu.com/download/games/?os=android&r=kanqiu
             */

            private String game_name;
            private String logo_img;
            private String game_size;
            private String type_name;
            private String down_count;
            private String ios;
            private String android;

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

            public String getDown_count() {
                return down_count;
            }

            public void setDown_count(String down_count) {
                this.down_count = down_count;
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
        }

        public static class GameDetailsBean  implements Serializable{

            /**
             * feature_img : [{"url":"/game/testimg/63365ce734a9c0cd0554ce23c253fb85.jpg"},{"url":"/game/testimg/63365ce734a9c0cd0554ce23c253fb85.jpg"}]
             * game_details : 经典1.76传奇动作手游《王者传奇》震撼来袭！游戏继承“传奇”的核心玩法，延续了传奇世界的经典设定，完美复刻顶级蓝光画质。炫酷套装，百人同屏PK，重温经典传奇。
             */

            private String game_details;
            private List<FeatureImgBean> feature_img;


            public String getGame_details() {
                return game_details;
            }

            public void setGame_details(String game_details) {
                this.game_details = game_details;
            }

            public List<FeatureImgBean> getFeature_img() {
                return feature_img;
            }

            public void setFeature_img(List<FeatureImgBean> feature_img) {
                this.feature_img = feature_img;
            }

            public static class FeatureImgBean  implements Serializable {
                @Override
                public String toString() {
                    return "FeatureImgBean{" +
                            "url='" + url + '\'' +
                            '}';
                }

                /**
                 * url : /game/testimg/63365ce734a9c0cd0554ce23c253fb85.jpg
                 */

                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
        public static class CommentsBean {
            @Override
            public String toString() {
                return "CommentsBean{" +
                        "id=" + id +
                        ", time='" + time + '\'' +
                        ", content='" + content + '\'' +
                        ", user_name='" + user_name + '\'' +
                        ", head_img='" + head_img + '\'' +
                        ", num=" + num +
                        ", list=" + list +
                        '}';
            }

            /**
             * id : 8
             * time : 2019-06-05
             * content : werfwe
             * user_name : Test002
             * head_img : http://www.52wag.com/game/testimg/default.png
             * list : [{"id":9,"content":"1","user_name":"Test003","user_id":107},{"id":10,"content":"2","user_name":"Test003","user_id":107},{"id":11,"content":"3","user_name":"Test003","user_id":107},{"id":12,"content":"4","user_name":"Test003","user_id":107},{"id":13,"content":"5","user_name":"Test003","user_id":107},{"id":14,"content":"6","user_name":"Test003","user_id":107},{"id":15,"content":"7","user_name":"Test003","user_id":107},{"id":16,"content":"8","user_name":"Test003","user_id":107},{"id":17,"content":"9","user_name":"Test003","user_id":107},{"id":18,"content":"10","user_name":"Test003","user_id":107},{"id":19,"content":"11","user_name":"Test003","user_id":107},{"id":20,"content":"12","user_name":"Test003","user_id":107},{"id":21,"content":"13","user_name":"Test003","user_id":107},{"id":22,"content":"14","user_name":"Test003","user_id":107}]
             * num : 14
             */

            private int id;
            private String time;
            private String content;
            private String user_name;
            private String head_img;
            private int num;
            private List<CommentsBean.ListBean> list;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public List<CommentsBean.ListBean> getList() {
                return list;
            }

            public void setList(List<CommentsBean.ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * id : 9
                 * content : 1
                 * user_name : Test003
                 * user_id : 107
                 */

                private int id;
                private String content;
                private String user_name;
                private int user_id;

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

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }
            }
        }
        public static class KacListBean  implements Serializable{
            /**
             * id : 1
             * name : 贵族礼包
             * content : 高级守卫令牌，金条(大)，斧子，副本卷轴，玛雅卷轴
             * residue : 4996
             * status : 1
             */

            private int id;
            private String name;
            private String content;
            private int residue;
            private int status;
            private String cdkey;

            public String getCdkey() {
                return cdkey;
            }

            public void setCdkey(String cdkey) {
                this.cdkey = cdkey;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getResidue() {
                return residue;
            }

            public void setResidue(int residue) {
                this.residue = residue;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
