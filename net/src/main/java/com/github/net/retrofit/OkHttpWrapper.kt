package com.github.net.retrofit

import android.content.Context
import com.github.net.cookies.PersisentCookieStore
import okhttp3.*
import java.util.concurrent.TimeUnit

/**
 * Created by Lyongwang on 2020/9/27 17: 53.
 *
 * Email: liyongwang@yiche.com
 */
class OkHttpWrapper private constructor(){

    private val writeTimeoutSecond: Long = 30
    private val pingIntervalSecond: Long = 10
    private val readTimeoutSecond: Long = 30
    private val connectTimeOutSecond: Long = 30

    private lateinit var cookieStore:PersisentCookieStore
    private var interceptors: List<Interceptor> = ArrayList()
    private var networkInterceptors: List<Interceptor> = ArrayList()

    private lateinit var mOkHttp: OkHttpClient

    /**
     * Okhttp初始化
     */
    internal fun init(context: Context){
        val cookieJar = object:CookieJar{
            val store = PersisentCookieStore(context)
            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                return store.get(url.host).toList()
            }

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                store.add(cookies.toMutableList())
            }

        }
        val builder = OkHttpClient().newBuilder()
                .connectTimeout(connectTimeOutSecond, TimeUnit.SECONDS)
                .readTimeout(readTimeoutSecond, TimeUnit.SECONDS)
                .pingInterval(pingIntervalSecond, TimeUnit.SECONDS)
                .writeTimeout(writeTimeoutSecond, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(10, 1, TimeUnit.MINUTES))

        initInterceptors(builder, interceptors)
        initNetworkInterceptors(builder, networkInterceptors)
        builder.cookieJar(cookieJar)
        mOkHttp = builder.build()

        mOkHttp.dispatcher.maxRequests = 96;
        mOkHttp.dispatcher.maxRequestsPerHost = 20
    }

    internal fun client(): OkHttpClient {
        return mOkHttp
    }

    private fun initNetworkInterceptors(builder: OkHttpClient.Builder?, networkInterceptors: List<Interceptor>?) {
        networkInterceptors?.forEach{
            interceptor -> builder?.addNetworkInterceptor(interceptor)
        }
    }

    private fun initInterceptors(builder: OkHttpClient.Builder?, interceptors: List<Interceptor>?) {
        interceptors?.forEach{
            interceptor -> builder?.addInterceptor(interceptor)
        }
    }

    companion object{
        internal val instance = Holder.holder
    }

    private object Holder{
        val holder = OkHttpWrapper()
    }

    fun addInterceptors(interceptors: List<Interceptor>): OkHttpWrapper?{
        this.interceptors = interceptors
        return this
    }

    fun addNetworkInterceptors(interceptors: List<Interceptor>): OkHttpWrapper?{
        networkInterceptors = interceptors
        return this
    }

    fun clearCookies(){
        cookieStore?.clear()
    }

}
