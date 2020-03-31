package com.github.leeon;

import com.github.common.CommonInit;
import com.github.common.base.BaseApplication;
import com.github.common.bundle.Bundles;

/**
 * Created by Lyongwang on 2019-11-11 13: 59.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class LApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonInit.init(this, BuildConfig.DEBUG);
        Bundles.getInstance().init();
    }
}
