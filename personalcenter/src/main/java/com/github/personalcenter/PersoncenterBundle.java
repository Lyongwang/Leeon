package com.github.personalcenter;

import android.util.Log;

import com.github.annotation.bundle.BundleCode;
import com.github.annotation.bundle.BundleInfo;
import com.github.annotation.bundle.BundlePropery;
import com.github.common.bundle.IBundle;
import com.github.common.event.eventor.IEventor;
import com.github.common.event.eventor.LoginEventor;

/**
 * Created by Lyongwang on 2020-02-14 12: 07.
 * <p>
 * Email: liyongwang@yiche.com
 */
@BundleInfo(p = BundlePropery.DEFAULT, c = BundleCode.PERSONCENTER, v = "1.0", n = "personcenter", d = "用户中心")
public class PersoncenterBundle implements IBundle {
    private static final String TAG = "PersoncenterBundle";

    @Override
    public void onCreate() {
        Log.i(TAG, "PersoncenterBundle onCreate: ");
    }

    @Override
    public void onDestory() {
        Log.i(TAG, "PersoncenterBundle onDistory: ");
    }

    @Override
    public void onEvent(IEventor iEventor) {
        if (iEventor instanceof LoginEventor) {
            Log.i(TAG, "onEvent: " + ((LoginEventor) iEventor).getLoginState());
        }
    }
}
