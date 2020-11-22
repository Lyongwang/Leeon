package com.github.loginlib.presenter

import android.util.Log
import android.widget.Toast
import com.github.common.event.EventCenter
import com.github.common.event.eventor.LoginEventor
import com.github.common.tools.encryptDES
import com.github.common.tools.toJson
import com.github.common.tools.toast
import com.github.loginlib.bean.UserModel
import com.github.loginlib.constant.ParamKey
import com.github.loginlib.constant.Urls
import com.github.loginlib.view.contract.LoginContract
import com.github.net.INetWorkCallBack
import com.github.net.NetWork
import com.github.net.bean.HttpResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Lyongwang on 2020/9/10 15: 44.
 *
 * Email: liyongwang@yiche.com
 */
class LoginPresenter(iView: LoginContract.IView): LoginContract.IPresenter {

    private val tag: String = "LoginPresenter"
    private val mView:LoginContract.IView = iView

    override fun loginByName(userName: String, pwd: String) {
        val params = mapOf(ParamKey.loginName to userName,
                ParamKey.userPwd to pwd)
        val loginByName = NetWork.getService(LoginService::class.java).loginByName(params.toJson().encryptDES())
        NetWork.request(loginByName)
                .callBack(object :INetWorkCallBack<UserModel> {
                    override fun onError(throwable: Throwable) {
                        throwable.message?.toast(Toast.LENGTH_LONG)
                        Log.i(tag, "onError: ${throwable.message}")
                        mView.loginError()
                    }

                    override fun onSuccess(response: HttpResult<UserModel>) {
                        Log.i(tag, "onSuccess: $response")
                        if (response.isSuccess()){
                            response.data?.saveToSp()
                            EventCenter.event(LoginEventor.obtain(true))
                            mView.loginSuccess()
                        }
                    }
                }).execute()
    }

    override fun loginByDynamicPwd(phoneNum: String, dynamicPwd: String) {
        TODO("Not yet implemented")
    }


}

interface LoginService{
    @POST(Urls.LOGIN_BY_NAME)
    @FormUrlEncoded
    fun loginByName(@Field(ParamKey.token) token:String): Observable<HttpResult<UserModel>>
}