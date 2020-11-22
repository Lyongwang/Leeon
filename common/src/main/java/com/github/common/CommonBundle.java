package com.github.common;

import com.github.annotation.bundle.BundleCode;
import com.github.annotation.bundle.BundleInfo;
import com.github.annotation.bundle.BundlePropery;
import com.github.common.bundle.IBundle;
import com.github.common.event.eventor.IEventor;

/**
 * Created by Lyongwang on 2020/11/22 15: 30.
 * <p>
 * Email: liyongwang@yiche.com
 */
@BundleInfo(p = BundlePropery.HIGH, c = BundleCode.COMMON, v = "1.0", n = "login", d = "登录注册")
public class CommonBundle implements IBundle {
    @Override
    public void onCreate() {
    }

    @Override
    public void onDestory() {
    }

    @Override
    public void onEvent(IEventor iEventor) {

    }
}
