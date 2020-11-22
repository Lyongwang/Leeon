package com.github.loginlib;

import android.util.Log;

import com.github.annotation.bundle.BundleCode;
import com.github.annotation.bundle.BundleInfo;
import com.github.annotation.bundle.BundlePropery;
import com.github.common.bundle.IBundle;
import com.github.common.event.eventor.IEventor;
import com.github.common.event.eventor.LoginEventor;

/**
 * Created by Lyongwang on 2020-02-14 14: 42.
 * <p>
 * Email: liyongwang@yiche.com
 */
@BundleInfo(p = BundlePropery.HIGH, c = BundleCode.LOGIN, v = "1.0", n = "login", d = "登录注册")
public class LoginBundle implements IBundle {
    private static final String TAG = "IBundle";

    @Override
    public void onCreate() {
        Log.i(TAG, "LoginBundle onCreate: ");
    }

    @Override
    public void onDestory() {
        Log.i(TAG, "LoginBundle onDistory: ");
    }

    @Override
    public void onEvent(IEventor iEventor) {
        if (iEventor instanceof LoginEventor){
            if (((LoginEventor) iEventor).getLoginState()){
                // 登录
                Log.i(TAG, "onEvent: 登录成功");
            } else {
                // 登出
                Log.i(TAG, "onEvent: 登出成功");
            }
        }
    }
}
