package com.github.common.bundle;

/**
 * Created by Lyongwang on 2020/3/31 15: 43.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class BundleProxyFactory {
    public static BundleProxy getBundleProxy(String bundleClsName, BundleInfo info) {
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
