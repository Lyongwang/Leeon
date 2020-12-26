package com.github.loginlib.view.contract

/**
 * Created by Lyongwang on 2020/9/10 15: 47.
 *
 * Email: liyongwang@yiche.com
 */
interface LoginContract {
    interface IPresenter {
        fun loginByName(userName: String, pwd: String)
        fun loginByDynamicPwd(phoneNum: String, dynamicPwd: String)

    }

    interface IView{
        /**
         * 登录成功
         */
        fun loginSuccess()

        /**
         * 登录失败
         */
        fun loginError()

    }
}