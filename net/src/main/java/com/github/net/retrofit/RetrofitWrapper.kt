package com.github.net.retrofit

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Lyongwang on 2020/10/17 17: 15.
 *
 * Email: liyongwang@yiche.com
 */
class RetrofitWrapper private constructor() {
    private val mRetrofit:Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(OkHttpWrapper.instance().client())
            .build()

    companion object{
        internal fun init(context: Context){
            OkHttpWrapper.instance().init(context)
        }
        internal fun instance() = Holder.instance
    }

    private object Holder{
        val instance: RetrofitWrapper = RetrofitWrapper()
    }

    internal fun <T> getRequest(clazz:Class<T>): T {
        return mRetrofit.create(clazz)
    }


}