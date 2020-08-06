package com.wag.gamebox.entity;

/**
 * Created by James on 2019/4/28.
 */
public class NameAuthResult {
    /**
     * code : 200
     * msg : 认证成功
     * time : 1542251113
     * data : null
     */

    private String code;
    private String msg;
    private String time;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
