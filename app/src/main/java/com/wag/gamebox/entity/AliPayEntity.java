package com.wag.gamebox.entity;

/**
 * Created by James on 2018/11/2.
 */
public class AliPayEntity {

    /**
     * code : 200
     * msg : 请求成功
     * time : 1541757184
     * data : alipay_sdk=alipay-sdk-php-20180705&app_id=2018102561797524&biz_content=%7B%22body%22%3A%22%E6%B8%B8%E6%88%8F%E5%B9%B3%E5%8F%B0%22%2C%22subject%22%3A+%22%E6%B8%B8%E6%88%8F%E5%B8%81%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A+%22APPZFB2018110949575050%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.1%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=www.i89.tv%2FLoveBump&sign_type=RSA2&timestamp=2018-11-09+17%3A53%3A05&version=1.0&sign=OeugxwKRzt1SGXd9hVztdMLcp7XUQsk3SqKyDEQQo9xaXVTGg0pGxj8%2FU2mMNnNmEvWwDGyzjuCIIiRpKxNalJ%2FQeK9ONqM5d0jHaUZhjnZr9cJmFIkeIaJf%2Bs58imitbGtYB5jWXeGnlDgY5g%2B6HMvF%2BzTHo%2FTRPgypHgej%2Bq2%2BhwwRKUUNIwTL4dN8UZm8wi1MfL%2BJRtFajXogQsXt%2BEZxPBEoGzRI2ROAN1lUwOP6viurDtJVVJ7xpHnxlFZBzm%2B4DAKmaBnQ2yDnpzFvwVFhXgORHb1X9f5Q90eaqWdC1qP3CgG%2FGtpTDxLvz0WXqCy0s%2BXiV6RDKusTPa6ZVA%3D%3D
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
