package com.github.loginlib.bean

import androidx.annotation.IntegerRes

/**
 * Created by Lyongwang on 2020/9/10 15: 55.
 *
 * Email: liyongwang@yiche.com
 */
enum class LoginType(val type: Int) {
    /**
     * 易车登录
     */
    YICHE_LOGIN(-1),
    /**
     * 新浪登录
     */
    XINLANG_LOGIN(1),
    /**
     * 腾讯登录
     */
    TECENT_LOGIN(2),
    /**
     * 微信登录
     */
    WEIXIN_lOGIN(6),
    /**
     * 动态登录
     */
    DYNAMIC_LOGIN(100),
    /**
     * 一键登录
     */
    ELOGIN(100001),
    /**
     * 未登录
     */
    NO_LOGIN(-1000)
}