package com.github.net

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.net.interceptor.CookieInterceptor
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Created by Lyongwang on 2020/9/27 17: 53.
 *
 * Email: liyongwang@yiche.com
 */
class OkHttpWrapper {



    companion object{
        private lateinit var mOkHttp: OkHttpClient
        private var mDebug: Boolean = false
        private lateinit var mApp: Application

        @JvmStatic
        @JvmOverloads
        fun init(app: Application, debug: Boolean, vararg interceptors:Interceptor){
            mApp = app
            mDebug = debug
            var builder = OkHttpClient().newBuilder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .pingInterval(10, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectionPool(ConnectionPool(10, 1, TimeUnit.MINUTES))
            for (interceptor in interceptors) {
                builder.addInterceptor(interceptor)
            }
            builder.addInterceptor(CookieInterceptor())
            if (debug){
                builder.addInterceptor(StethoInterceptor())
            }



        }
    }
}
