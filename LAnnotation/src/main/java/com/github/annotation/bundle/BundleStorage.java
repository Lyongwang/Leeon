package com.github.annotation.bundle;

import java.util.HashMap;

/**
 * Created by Lyongwang on 2020/3/31 14: 37.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class BundleStorage {

    private static HashMap<String, BundleData> bundles = new HashMap<>();

    /**
     * 添加一个bundle
     * @param bundle
     */
    public static void put(String className, BundleData bundle){
        bundles.put(className, bundle);
    }

    public static HashMap<String, BundleData> getBundles(){
        return bundles;
    }

    /**
     * 清理bundle
     */
    public void clear(){
        if (bundles.size() > 0){
            bundles.clear();
        }
    }
}
