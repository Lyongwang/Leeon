package com.github.net

import android.annotation.SuppressLint
import android.content.ComponentCallbacks
import android.content.Context
import com.github.net.bean.HttpResult
import com.github.net.retrofit.RetrofitWrapper
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * Created by Lyongwang on 2020/11/21 14: 03.
 *
 * Email: liyongwang@yiche.com
 */
class NetWork {
    companion object {
        /**
         * 网络库初始化
         */
        fun init(context: Context) {
            RetrofitWrapper.instance().init(context)
        }

        /**
         * 获取网络请求服务
         */
        fun <T> getService(tClass: Class<T>): T {
            return RetrofitWrapper.instance().getService(tClass)
        }

        /**
         * 发起网络请求
         */
        fun <T> request(service: Observable<HttpResult<T>>): Request<T> {
            return Request(service)
        }
    }
}

class Request<T>(service: Observable<HttpResult<T>>) {
    private var mService = service
    private lateinit var mCallBack: INetWorkCallBack

    fun callBack(callback: INetWorkCallBack): Request<T> {
        mCallBack = callback
        return this
    }

    @SuppressLint("CheckResult")
    fun execute() {
        mService.subscribe({
            mCallBack.onSuccess(it)
        }, {
            mCallBack.onError(it)
        })
    }
}

interface INetWorkCallBack {
    fun <T> onSuccess(result: HttpResult<T>)

    fun onError(throwable: Throwable)
}
