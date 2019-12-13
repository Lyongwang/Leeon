package com.github.common.base;

/**
 * Created by Lyongwang on 2019-11-11 15: 35.
 * <p>
 * App信息类(包含application debug状态等信息)
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
