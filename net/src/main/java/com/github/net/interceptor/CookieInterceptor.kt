package com.github.net.interceptor

import com.github.net.OkHttpWrapper
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Lyongwang on 2020/9/27 18: 49.
 *
 * Email: liyongwang@yiche.com
 */
class CookieInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        OkHttpWrapper.saveCookies(request.url, response.headers)
        return response
    }
}