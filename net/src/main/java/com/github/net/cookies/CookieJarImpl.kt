package com.github.net.cookies

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Created by Lyongwang on 2020/10/11 16: 43.
 *
 * Email: liyongwang@yiche.com
 */
class CookieJarImpl constructor(val cookiePreference: CookiePreference): CookieJar {

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookiePreference.getCookie(url)
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookiePreference.putCookie(url, cookies)
    }
}