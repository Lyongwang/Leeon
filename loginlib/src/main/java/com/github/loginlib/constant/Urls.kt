package com.github.loginlib.constant

/**
 * Created by Lyongwang on 2020/11/21 14: 56.
 *
 * Email: liyongwang@yiche.com
 */

class Urls {
    companion object {
        private const val LOGIN_HOST: String = "https://appapi-gw.yiche.com/"

        const val LOGIN_BY_NAME: String = LOGIN_HOST + "app-biz-svc/user/login"
        const val LOGIN_BY_MOBILE: String = LOGIN_HOST + "app-biz-svc/user/v2/mobilelogin"
    }
}