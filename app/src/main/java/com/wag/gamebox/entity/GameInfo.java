package com.wag.gamebox.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "GameInfo")
public class GameInfo {
    /**
     * id : 1
     * game_name : 王者传奇
     * logo_img : /game/testimg/3c76aacec67c183de0d813149ce5357f.png
     * ios : https://cavalry.wuming.com/ze3y2w6/u5y33/1009001
     * android : https://cavalry.wuming.com/ze3y2w6/u5y33/1009001
     * game_size : 170M
     * type_name : 传奇类
     * game_details : 经典1.76传奇动作手游《王者传奇》震撼来袭！游戏继承“传奇”的核心玩法，延续了传奇世界的经典设定，完美复刻顶级蓝光画质。炫酷套装，百人同屏PK，重温经典传奇。
     */
    @Column(name = "id", isId = true, autoGen = false)
    private int id;

    @Column(name = "game_name")
    private String game_name;

    @Column(name = "logo_img")
    private String logo_img;

    @Column(name = "game_one_details")
    private String game_one_details;

    public String getGame_one_details() {
        return game_one_details;
    }

    public void setGame_one_details(String game_one_details) {
        this.game_one_details = game_one_details;
    }

    @Column(name = "img")
    private String img;

    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Column(name = "android")
    private String android;

    @Column(name = "game_size")
    private String game_size;

    @Column(name = "type_name")
    private String type_name;

    @Column(name = "game_details")
    private String game_details;

    @Column(name = "btn_tatus")
    private int btn_tatus;

    @Column(name = "zhong")
    private int zhong;                   //2正在下载

    @Column(name = "lishi")
    private int lishi;                      //2历史

    @Column(name = "xia")
    private int xia;                      //2下载

    @Column(name = "progress")
    private Double progress;        //当前进度

    @Column(name = "zsize")
    private Double zsize;            //总大小

    @Column(name = "package_name")
    private String android_package_name;    //游戏包名

    private int num;        //排行

    private String old_version;

    public String getOld_version() {
        return old_version;
    }

    public void setOld_version(String old_version) {
        this.old_version = old_version;
    }

    public String getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(String android_version) {
        this.android_version = android_version;
    }

    public String getUpdate_info() {
        return update_info;
    }

    public void setUpdate_info(String update_info) {
        this.update_info = update_info;
    }

    private String android_version;
    private String update_info;

    private String down_count;

    public String getDown_count() {
        return down_count;
    }

    public void setDown_count(String down_count) {
        this.down_count = down_count;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private String open_time;

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    private String spent;

    public String getSpent() {
        return spent;
    }

    public void setSpent(String spent) {
        this.spent = spent;
    }

    private String ios;

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

    public String getGame_details() {
        return game_details;
    }

    public void setGame_details(String game_details) {
        this.game_details = game_details;
    }

    public int getBtn_tatus() {
        return btn_tatus;
    }

    public void setBtn_tatus(int btn_tatus) {
        this.btn_tatus = btn_tatus;
    }

    public int getZhong() {
        return zhong;
    }

    public void setZhong(int zhong) {
        this.zhong = zhong;
    }

    public int getLishi() {
        return lishi;
    }

    public void setLishi(int lishi) {
        this.lishi = lishi;
    }

    public int getXia() {
        return xia;
    }

    public void setXia(int xia) {
        this.xia = xia;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Double getZsize() {
        return zsize;
    }

    public void setZsize(Double zsize) {
        this.zsize = zsize;
    }

    public String getPackage_name() {
        return android_package_name;
    }

    public void setPackage_name(String package_name) {
        this.android_package_name = package_name;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "id=" + id +
                ", game_name='" + game_name + '\'' +
                ", logo_img='" + logo_img + '\'' +
                ", img='" + img + '\'' +
                ", level=" + level +
                ", android='" + android + '\'' +
                ", game_size='" + game_size + '\'' +
                ", type_name='" + type_name + '\'' +
                ", game_details='" + game_details + '\'' +
                ", btn_tatus=" + btn_tatus +
                ", zhong=" + zhong +
                ", lishi=" + lishi +
                ", xia=" + xia +
                ", progress=" + progress +
                ", zsize=" + zsize +
                ", android_package_name='" + android_package_name + '\'' +
                ", num=" + num +
                ", old_version='" + old_version + '\'' +
                ", android_version='" + android_version + '\'' +
                ", update_info='" + update_info + '\'' +
                ", down_count='" + down_count + '\'' +
                ", open_time='" + open_time + '\'' +
                ", spent='" + spent + '\'' +
                ", ios='" + ios + '\'' +
                '}';
    }
}