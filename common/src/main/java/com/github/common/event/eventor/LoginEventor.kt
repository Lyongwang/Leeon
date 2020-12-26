package com.github.common.event.eventor

/**
 * Created by Lyongwang on 2020/11/22 14: 54.
 *
 * Email: liyongwang@yiche.com
 */
class LoginEventor private constructor(state: Boolean): IEventor {
    val loginState: Boolean = state

    companion object{
        /**
         * 登录事件 true 标识已登录; false 标识未登录
         */
        fun obtain(state: Boolean): LoginEventor {
            return LoginEventor(state)
        }
    }
}