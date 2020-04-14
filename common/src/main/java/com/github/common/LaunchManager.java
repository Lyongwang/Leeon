package com.github.common;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;
import android.os.Bundle;

import com.github.annotation.router.RouterTable;
import com.github.common.router.LRouter;

/**
 * Created by Lyongwang on 2020/4/13 16: 40.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class LaunchManager implements Application.ActivityLifecycleCallbacks {
    private static final String MAIN_ACTIVITY_NAME = "com.github.leeon.MainActivity";

    private LaunchManager() {
    }

    public static LaunchManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void start(Activity activity, Uri uri) {
        LRouter.buildWithUri(uri).go(activity);
    }

    private static class SingletonHolder{
        private static final LaunchManager INSTANCE = new LaunchManager();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
