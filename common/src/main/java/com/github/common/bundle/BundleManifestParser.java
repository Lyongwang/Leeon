package com.github.common.bundle;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.PriorityQueue;

/**
 * Created by Lyongwang on 2019-11-11 16: 12.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class BundleManifestParser {
    public static PriorityQueue<BundleProxy> parse(Context context) {
        if (context == null){
            return null;
        }

        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (appInfo == null || appInfo.metaData == null){
            return null;
        }

        for (String key : appInfo.metaData.keySet()){
//            if ()
        }

        return null;
    }
}
