package com.wag.gamebox.entity;

/**
 * Created by James on 2018/10/17.
 */
public class GetPhoneCodeResult {
    /**
     * code : 200
     * msg : 发送成功
     * time :
     * data :
     */

    private String code;
    private String msg;
    private String time;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
