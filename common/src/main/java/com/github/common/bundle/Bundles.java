package com.github.common.bundle;

import com.github.common.base.AppInfo;

import java.util.PriorityQueue;

/**
 * Created by Lyongwang on 2019-11-11 15: 30.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class Bundles {
    private static PriorityQueue<BundleProxy> mProxies;

    public static Bundles getInstance(){
        return SingletonHolder.instance;
    }

    private static class SingletonHolder{
        private static final Bundles instance = new Bundles();
    }

    private Bundles(){
    }

    public void init(){
        // 1 解析组件
        mProxies = BundleManifestParser.parse(AppInfo.getApplication());
    }

    /**
     * 启动组件
     */
    public void start(){
        if (mProxies.isEmpty()){
            return;
        }
        while (!mProxies.isEmpty()){
            BundleProxy proxy = mProxies.poll();
            if (!proxy.isRuning()) {
                proxy.onCreate();
            }
        }
    }

}
