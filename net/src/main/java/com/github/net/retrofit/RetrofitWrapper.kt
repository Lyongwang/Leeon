package com.github.net.retrofit

import android.content.Context
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Lyongwang on 2020/10/17 17: 15.
 *
 * Email: liyongwang@yiche.com
 */
class RetrofitWrapper private constructor() {
    private val baseUrl: String = "https://appapi-gw.yiche.com/"
    private lateinit var mRetrofit:Retrofit

    companion object{
        internal val instance = Holder.instance
    }

    private object Holder{
        val instance = RetrofitWrapper()
    }

    /**
     * 获取一个requestService
     */
    internal fun <T> getService(clazz:Class<T>): T {
        return mRetrofit.create(clazz)
    }

    /**
     * 初始化OkHttp
     */
    internal fun init(context: Context){
        OkHttpWrapper.instance.init(context)
        mRetrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpWrapper.instance.client())
                .baseUrl(baseUrl)
                .build()
    }

}