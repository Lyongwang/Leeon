package com.github.net.bean

/**
 * Created by Lyongwang on 2020/10/17 18: 20.
 *
 * Email: liyongwang@yiche.com
 */
class HttpResult<T> constructor() {
    val SUCC_STATE = 1
    val LOGIN_EXPIRED = 10002

    /** 状态码 */
    var status = 0

    /** 状态信息 */
    var message: String? = null

    /** 具体数据 */
    var data: T? = null

    override fun toString(): String {
        return "HttpResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}'
    }

    /**
     * Description: 是否获取到正确数据
     * @return 是 true 否 false
     */
    fun isSuccess(): Boolean {
        return status == SUCC_STATE
    }

    fun loginExpired(): Boolean {
        return status == LOGIN_EXPIRED
    }

    /**
     * Description: 设置数据
     * @param data 具体数据
     * @return HttpResult对象
     */
    fun setData(data: T): HttpResult<T> {
        this.data = data
        return this
    }


    /**
     * 接口返回的原始数据json串，可能为null
     */
    var originJsonString: String? = null
}