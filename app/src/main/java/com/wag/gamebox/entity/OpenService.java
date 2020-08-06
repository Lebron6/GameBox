package com.wag.gamebox.entity;

import java.util.List;

/**
 * Created by James on 2018/10/26.
 */
public class OpenService {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1540545870
     * data : [{"open_time":"09-30 08:00","type_name":"三国slg","game_name":"仙妖劫","ios":"http://192.168.1.201/11.ipa","android":"https://imtt.dd.qq.com/16891/D8AF8D80001B513A374911FC1EF461F3.apk?fsname=com.meilishuo_9.5.2.2069_176.apk&csr=3554","id":2,"game_size":"175M","logo_img":"/game/testimg/3c76aacec67c183de0d813149ce5357f.png"},{"open_time":"09-10 08:00","type_name":"传奇类","game_name":"王者传奇","ios":"http://192.168.1.201/11.ipa","android":"https://imtt.dd.qq.com/16891/D8AF8D80001B513A374911FC1EF461F3.apk?fsname=com.meilishuo_9.5.2.2069_176.apk&csr=3554","id":1,"game_size":"170M","logo_img":"/game/testimg/3c76aacec67c183de0d813149ce5357f.png"},{"open_time":"10-13 08:00","type_name":"传奇类","game_name":"王者传奇","ios":"http://192.168.1.201/11.ipa","android":"https://imtt.dd.qq.com/16891/D8AF8D80001B513A374911FC1EF461F3.apk?fsname=com.meilishuo_9.5.2.2069_176.apk&csr=3554","id":1,"game_size":"170M","logo_img":"/game/testimg/3c76aacec67c183de0d813149ce5357f.png"},{"open_time":"10-12 08:00","type_name":"三国slg","game_name":"仙妖劫","ios":"http://192.168.1.201/11.ipa","android":"https://imtt.dd.qq.com/16891/D8AF8D80001B513A374911FC1EF461F3.apk?fsname=com.meilishuo_9.5.2.2069_176.apk&csr=3554","id":2,"game_size":"175M","logo_img":"/game/testimg/3c76aacec67c183de0d813149ce5357f.png"}]
     */

    private String code;
    private String msg;
    private String time;
    private List<GameInfo> data;

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

    public List<GameInfo> getData() {
        return data;
    }

    public void setData(List<GameInfo> data) {
        this.data = data;
    }


}
