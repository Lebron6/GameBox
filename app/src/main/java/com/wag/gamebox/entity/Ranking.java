package com.wag.gamebox.entity;

import java.util.List;

/**
 * Created by James on 2018/10/29.
 */
public class Ranking {
    /**
     * code : 200
     * msg : 请求成功
     * time : 1540793969
     * data : [{"id":6,"game_name":"真江湖","game_size":"250M","type_name":"战争策略","game_details":"经典1.76传奇动作手游《王者传奇》震撼来袭！","logo_img":"/game/testimg/7d8ac4bb15fca6fe6d2252ba9b2480d1.jpg","num":1},{"id":5,"game_name":"轩辕剑群侠录","game_size":"200M","type_name":"策略卡牌","game_details":"经典1.76传奇动作手游《王者传奇》震撼来袭！","logo_img":"/game/testimg/4667a5098c8d59f2fae7c52887b2be0d.png","num":2},{"id":4,"game_name":"加勒比海盗","game_size":"200M","type_name":"跳舞类","game_details":"经典1.76传奇动作手游《王者传奇》震撼来袭！","logo_img":"/game/testimg/f5115244d94b040eeb50e4b18b42dace.png","num":3},{"id":3,"game_name":"二战风云","game_size":"180M","type_name":"魔幻rpg","game_details":"经典1.76传奇动作手游《王者传奇》震撼来袭！","logo_img":"/game/testimg/e74c558e5383371dffd6ea234d6e7ab5.png","num":4},{"id":2,"game_name":"仙妖劫","game_size":"175M","type_name":"三国slg","game_details":"经典1.76传奇动作手游《王者传奇》震撼来袭！","logo_img":"/game/testimg/3c76aacec67c183de0d813149ce5357f.png","num":5},{"id":1,"game_name":"王者传奇","game_size":"170M","type_name":"传奇类","game_details":"经典1.76传奇动作手游《王者传奇》震撼来袭！","logo_img":"/game/testimg/3c76aacec67c183de0d813149ce5357f.png","num":6}]
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
