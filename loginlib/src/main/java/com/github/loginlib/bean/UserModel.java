package com.github.loginlib.bean;

import com.github.loginlib.tools.PreferenceUtil;

import java.util.List;

/**
 * 用户登录Model
 * Created by kenneth on 2017/5/16.
 */

public class UserModel {
    /**
     * 如果未绑定,返回0
     */
    public int               UserId = -1;
    /**
     * 用户名
     */
    public String            UserName;
    /**
     * 昵称(如果客户端提供的用户名在昵称检查时出现被占用,则生成一个新的昵称.否则,此值与用户名相同)
     */
    public String            NickName;
    /**
     * 头像
     */
    public String            UserAvatar;
    /**
     * 手机验证开关(true: 打开,false:关闭)
     */
    public boolean           EnableMobileValidate;
    /**
     * 昵称是否被占用(true:昵称被占用,false:昵称可用)
     */
    public boolean           IsExsitNickname;
    /**
     * 手机号
     */
    public String            Mobile;
    /**
     * 参数(JSON格式)DES加密后的字符串
     */
    public String            Token;
    /**
     * 登录 Cookie 列表
     */
    public List<YicheCookie> Cookies;

    /**
     * 登录后下发认证的cookie名称。add 6.8
     */
    public String  AuthCookieName;
    /**
     * 登录后后生成临时token，可用来获取userid，5分钟有效。add v7.5
     */
    public String  AuthToken;

    /**
     * 头像信息
     */
    public List<String> imgs;
    /**
     * 注册时间
     */
    public String RegisterTime;
    /**
     * 注册时 获取手机随机口令接口的返回token
     * temp 数据
     */
    public String token;
    /**
     * 昵称
     */
    public String ShowName;

    public void saveToSp() {
        PreferenceUtil.INSTANCE.setNickname(ShowName);
        PreferenceUtil.INSTANCE.setPhoneNum(Mobile);
        PreferenceUtil.INSTANCE.setAvatar(UserAvatar);
        PreferenceUtil.INSTANCE.setUserName(UserName);
        PreferenceUtil.INSTANCE.setUserId(UserId);
    }
}
