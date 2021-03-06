package com.wag.gamebox.api;

/**
 * Created by James on 2018/1/4.
 */

public class BaseUrl {

    private static BaseUrl baseUrl;

    public static BaseUrl getInstence() {
        if (baseUrl == null) {
            return new BaseUrl();
        }
        return baseUrl;
    }

//    public String ipAddress  = "http://192.168.1.201/";//本地服务器
//    public String ipAddress  = "http://www.quantark.com/";//线上服务器
//    public String ipAddress  = "http://www.52wag.com/";//线上服务器
    public String ipAddress  = "http://www.aoskytech.net/";//线上服务器

    /**
     * 首页数据
     * @return
     */
    public String getHomeDataUrl(){
        return ipAddress+"game/public/api/game/appgame/homePage";
    }

    /**
     * 热门游戏列表
     * @return
     */
    public String getHotGameListUrl(){
        return ipAddress+"game/public/api/game/appgame/hotGmaeMore";
    }

    /**
     * 礼包列表
     * @return
     */
    public String getGiftListUrl(){
        return ipAddress+"game/public/api/game/appgame/gameKac";
    }

    /**
     * 游戏详情
     * @return
     */
    public String getGameDetailsUrl(){
        return ipAddress+"game/public/api/game/appgame/gameDetails";
    }

    /**
     * 游戏礼包列表
     * @return
     */
    public String getAllGiftsOfGameUrl(){
        return ipAddress+"game/public/api/game/appgame/gameKacList";
    }

    /**
     * 领取礼包
     * @return
     */
    public String getReceiveGiftUrl(){
        return ipAddress+"game/public/api/game/appgame/getKac";
    }

    /**
     * 礼包详情
     * @return
     */
    public String getGiftDetailsUrl(){
        return ipAddress+"game/public/api/game/appgame/kacInfo";
    }

    /**
     * 资讯
     * @return
     */
    public String getInfoUrl(){
        return ipAddress+"game/public/api/game/appgame/information";
    }

    /**
     * 我的礼包
     * @return
     */
    public String getMyGiftUrl(){
        return ipAddress+"game/public/api/game/appgame/myKac";
    }

    /**
     * 我的礼包详情
     * @return
     */
    public String getMyGiftDetailsUrl(){
        return ipAddress+"game/public/api/game/appgame/myKacInfo";
    }

    /**
     * 游戏搜索
     * @return
     */
    public String getSearchGameUrl(){
        return ipAddress+"game/public/api/game/appgame/searchGame";
    }

    /**
     * 游戏分类接口
     * @return
     */
    public String getGameTypeUrl(){
        return ipAddress+"game/public/api/game/appgame/gameType";
    }

    /**
     * 游戏分类搜索
     * @return
     */
    public String getTypeSearchUrl(){
        return ipAddress+"game/public/api/game/appgame/typeSearch";
    }

    /**
     * 登录
     * @return
     */
    public String getAccountLoginUrl(){
        return ipAddress+"game/public/api/account/account/login";
    }

    /**
     * 注册
     * @return
     */
    public String getAccountRegisterUrl(){
        return ipAddress+"game/public/api/account/account/register";
    }

    /**
     * 获取验证码
     * @return
     */
    public String getPhoneCodeUrl(){
        return ipAddress+"game/public/api/account/smscode/appSendSms";
    }

    /**
     * 我的平台币
     * @return
     */
    public String getMyPTBUrl(){
        return ipAddress+"game/public/api/game/appgame/myCoin";
    }

    /**
     * 我的游戏
     * @return
     */
    public String getMyGameUrl(){
        return ipAddress+"game/public/api/game/appgame/myGame";
    }

    /**
     * 开服
     * @return
     */
    public String getAppOpenSrviceUrl(){
        return ipAddress+"game/public/api/game/appgame/getAppOpenSrvice";
    }

    /**
     * 我的页面
     * @return
     */
    public String getMyPageUrl(){
        return ipAddress+"game/public/api/game/appgame/myPage";
    }

    /**
     * 第三方登录
     * @return
     */
    public String getThirdLoginUrl(){
        return ipAddress+"game/public/api/account/account/thirdLogin";
    }

    /**
     * 第三方绑定手机号
     * @return
     */
    public String getThirdLoginBindPhoneUrl(){
        return ipAddress+"game/public/api/account/account/binding";
    }

    /**
     * 检测版本
     * @return
     */
    public String getVersionsUrl(){
        return ipAddress+"game/public/api/game/appgame/versions";
    }

    /**
     * 修改密码
     * @return
     */
    public String getFindPassUrl(){
        return ipAddress+"game/public/api/account/account/findPass";
    }

    /**
     * 登陆后修改密码
     * @return
     */
    public String getFindLoginPassUrl(){
        return ipAddress+"game/public/api/account/account/findLoginPass";
    }

    /**
     * 上传头像
     * @return
     */
    public String getUploadAvatarUrl(){
        return ipAddress+"game/public/api/game/appgame/uploadAvatar";
    }

    /**
     * 联系客服
     * @return
     */
    public String getCallServiceUrl(){
        return ipAddress+"game/public/api/game/appgame/service";
    }

    /**
     * 游戏排行榜
     * @return
     */
    public String getGameRankingUrl(){
        return ipAddress+"game/public/api/game/appgame/ranking";
    }

    /**
     * 游戏排行榜
     * @return
     */
    public String getAccountInfoUrl(){
        return ipAddress+"game/public/api/game/appgame/accountInfo";
    }

    /**
     * 意见反馈
     * @return
     */
    public String getShareAppUrl(){
        return ipAddress+"game/public/api/game/appgame/shareApp";
    }

    /**
     * 意见反馈
     * @return
     */
    public String getFeedBackUrl(){
        return ipAddress+"game/public/api/game/appgame/feedback";
    }

    /**
     * 获取微信AccessToken
     * @return
     */
    public String getWXAccessTokenUrl(){
        return "https://api.weixin.qq.com/sns/oauth2/access_token?";
    }

    /**
     * 获取微信个人信息
     * @return
     */
    public String getWXUserInfoUrl(){
        return "https://api.weixin.qq.com/sns/userinfo?";
    }

    /**
     * 获取微博个人信息
     * @return
     */
    public String getWBUserInfoUrl(){
        return "https://api.weibo.com/2/users/show.json";
    }

    /**
     * 微信支付
     * @return
     */
    public String getWXPayUrl(){
        return ipAddress+"game/public/api/game/wxpay/appWxpay";
    }

    /**
     * 支付宝支付
     * @return
     */
    public String getAliPayUrl(){
        return ipAddress+"game/public/api/game/alipay/appAlipay";
    }

    /**
     * 实名认证信息
     * @return
     */
    public String getNameAuthInfoUrl(){
        return ipAddress+"game/public/api/game/appgame/autheInfo";
    }

    /**
     * 实名认证
     * @return
     */
    public String getNameAuthUrl(){
        return ipAddress+"game/public/api/game/appgame/realAuthe";
    }

    /**
     * VIP特权
     * @return
     */
    public String getVIPRightUrl(){
        return ipAddress+"game/public/api/game/game/vip";
    }

    /**
     * VIP充值折扣
     * @return
     */
    public String getVIPDiscountUrl(){
        return ipAddress+"game/public/api/game/appgame/vip_open";
    }

    /**
     * 获取评论详情
     * @return
     */
    public String getCommentDeatailsUrl(){
        return ipAddress+"game/public/api/game/appgame/game_comments";
    }

    /**
     * 评论
     * @return
     */
    public String getCommentUrl(){
        return ipAddress+"game/public/api/game/appgame/comments";
    }

    /**
     * 回复
     * @return
     */
    public String getCommentCallBackUrl(){
        return ipAddress+"game/public/api/game/appgame/return_comments";
    }

    /**
     * 我的评论
     * @return
     */
    public String getMyCommentUrl(){
        return ipAddress+"game/public/api/game/appgame/my_comment";
    }

    /**
     * 更新管理
     * @return
     */
    public String getUpdataMangerUrl(){
        return ipAddress+"game/public/api/game/appgame/update_info";
    }

    /**
     * 任务列表
     * @return
     */
    public String getTaskListUrl(){
        return ipAddress+"game/public/api/game/appgame/task";
    }

    /**
     * 领取奖励
     * @return
     */
    public String getVoucherUrl(){
        return ipAddress+"game/public/api/game/appgame/get_rewards";
    }

    /**
     * 代金券列表
     * @return
     */
    public String getVoucherListUrl(){
        return ipAddress+"game/public/api/game/appgame/chit";
    }

    /**
     * 修改昵称
     * @return
     */
    public String getUpdataNickNameUrl(){
        return ipAddress+"game/public/api/game/appgame/edit_name";
    }

    /**
     * 任务详情
     * @return
     */
    public String getTaskDetailsUrl(){
        return ipAddress+"game/public/api/game/appgame/task_info";
    }
}
