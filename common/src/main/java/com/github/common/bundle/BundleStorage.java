package com.github.common.bundle;

import java.util.PriorityQueue;

/**
 * Created by Lyongwang on 2020/3/31 14: 37.
 * <p>
 * Email: liyongwang@yiche.com
 */
public abstract class BundleStorage {
    private static final int INIT_BUNDLES = 2;

    private static PriorityQueue<BundleProxy> bundles =
            new PriorityQueue<>(INIT_BUNDLES, (proxy1, proxy2) -> proxy1.getConfig().getPropery().intValue() - proxy2.getConfig().getPropery().intValue());

    /**
     * 添加一个bundle
     * @param proxy
     */
    public void addBundleProxy(BundleProxy proxy){
        if (bundles.size() >= INIT_BUNDLES) {
            throw new RuntimeException("Bundle num is to match!!!");
        }
        bundles.add(proxy);
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
