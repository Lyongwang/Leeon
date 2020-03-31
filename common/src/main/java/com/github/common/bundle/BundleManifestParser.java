package com.github.common.bundle;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Lyongwang on 2019-11-11 16: 12.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class BundleManifestParser {
    private static final String BUNDLE_NAME = "IBundle";

    public static PriorityQueue<BundleProxy> parse(Context context) {
        if (context == null) {
            return null;
        }

        PriorityQueue<BundleProxy> bundleProxies = new PriorityQueue<>(2,
                (proxy1, proxy2) -> proxy2.getConfig().getPropery().intValue() - proxy1.getConfig().getPropery().intValue());

        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (appInfo == null || appInfo.metaData == null) {
            return null;
        }

        for (String key : appInfo.metaData.keySet()) {
            if (BUNDLE_NAME.equals(appInfo.metaData.get(key))) {
                bundleProxies.add(getBundleConfig(key));
            }
        }
        return bundleProxies;
    }

    private static BundleProxy getBundleConfig(String bundleClsName) {
        Object bundle = null;
        Class<?> cls = null;
        try {
            cls = Class.forName(bundleClsName);
            bundle = cls.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        if (cls == null) {
            throw new RuntimeException("bundle name " + bundleClsName + " cannot get real class");
        }

        if (bundle == null) {
            throw new RuntimeException("bundle is null");
        }

        if (!(bundle instanceof IBundle)) {
            throw new RuntimeException("bundle must implement IBundle interface");
        }

        BundleInfo bundleInfo = cls.getAnnotation(BundleInfo.class);
        return new BundleProxy.Builder().setiBundle((IBundle) bundle).setInfo(bundleInfo).build();
    }
}
