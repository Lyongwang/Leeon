package com.github.common.base;

/**
 * Created by Lyongwang on 2019-11-11 15: 35.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class AppInfo {
    private static BaseApplication APPLICATION;
    private static boolean DEBUG;

    public static void init(BaseApplication application, boolean debug) {
        APPLICATION = application;
        DEBUG = debug;
    }

    public static BaseApplication getApplication(){
        return APPLICATION;
    }

    public static boolean isDebug(){
        return DEBUG;
    }
}
