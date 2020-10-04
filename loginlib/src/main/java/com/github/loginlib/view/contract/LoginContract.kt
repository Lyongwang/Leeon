package com.github.loginlib.view.contract

import com.github.loginlib.bean.LoginType

/**
 * Created by Lyongwang on 2020/9/10 15: 47.
 *
 * Email: liyongwang@yiche.com
 */
interface LoginContract {
    interface IPresenter {
        fun login(loginType: LoginType)

    }

    interface IView{

    }
}