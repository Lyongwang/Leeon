package com.github.net.cookies

import okhttp3.Cookie
import okhttp3.HttpUrl

/**
 * Created by Lyongwang on 2020/10/11 16: 45.
 *
 * Email: liyongwang@yiche.com
 */
class CookiePreference {
    private val cookieMap = HashMap<String, List<Cookie>>()

    fun putCookie(url: HttpUrl, cookies: List<Cookie>) {
        cookieMap[url.host] =  cookies
    }

    fun getCookie(url: HttpUrl): List<Cookie> {
        return cookieMap[url.host] ?: ArrayList()
    }



}