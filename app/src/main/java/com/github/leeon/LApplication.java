package com.github.leeon;

import android.app.Application;

/**
 * Created by Lyongwang on 2019-11-11 13: 59.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class LApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Bundles.init(this, BuildConfig.DEBUG);
    }
}
