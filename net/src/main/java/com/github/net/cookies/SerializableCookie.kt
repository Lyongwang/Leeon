package com.github.net.cookies

import okhttp3.Cookie
import java.io.Serializable

/**
 * Created by Lyongwang on 2020/10/17 12: 57.
 *
 * Email: liyongwang@yiche.com
 */
class SerializableCookie(cookie: Cookie) : Serializable {

    private val name:String?
    private val value:String?
    private val domain:String?
    private val path:String?
    private val secure :Boolean?
    private val httpOnly :Boolean?
    private val hostOnly :Boolean?
    private val expiresAt :Long?


    init{
        name = cookie.name
        value = cookie.value
        domain = cookie.domain
        secure = cookie.secure
        expiresAt = cookie.expiresAt
        httpOnly = cookie.httpOnly
        hostOnly = cookie.hostOnly
        path = cookie.path
    }

    /**
     * 从当前对象中获取cookie
     */
    fun cookie(): Cookie{
        return Cookie.Builder()
                .name(name ?: "")
                .expiresAt(expiresAt ?: 0L)
                .value(value ?: "")
                .path(path ?: "")
                .let {
                    if(httpOnly == true) it.httpOnly()
                    if (secure == true) it.secure()
                    val domainSafe = domain ?: ""
                    if (hostOnly == true){
                        it.hostOnlyDomain(domainSafe)
                    } else {
                        it.domain(domainSafe)
                    }
                    it
                }.build()
    }
}