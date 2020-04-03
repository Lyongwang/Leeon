package com.github.common.bundle;

import android.util.Log;

import com.github.bundleannotation.BundleData;
import com.github.bundleannotation.BundleStorage;
import com.github.bundleannotation.Constains;
import com.github.bundleannotation.IBundleInit;
import com.github.common.base.AppInfo;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import dalvik.system.DexFile;

/**
 * Created by Lyongwang on 2019-11-11 15: 30.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class Bundles {
    private static final String TAG = Bundles.class.getSimpleName();
    private static PriorityQueue<BundleProxy> mProxies = new PriorityQueue<>(11, (proxy1, proxy2) ->
                    proxy1.getConfig().getPropery().intValue() - proxy2.getConfig().getPropery().intValue());

    public static Bundles getInstance(){
        return SingletonHolder.instance;
    }

    private static class SingletonHolder{
        private static final Bundles instance = new Bundles();
    }

    private Bundles(){
    }

    public void init(){
        try {
            DexFile dexFile = new DexFile(AppInfo.getApplication().getPackageCodePath());
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()){
                String className = entries.nextElement();
                if (className.startsWith(Constains.PACKAGE_NAME) &&
                        className.contains(Constains.CLASS_NAME_SEPARATOR.concat(Constains.CLASS_NAME_SUFIX))){
                    // 1 解析组件
                    initBundles(className);
                }
            }
            // 2 加载组件
            loadBundles();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBundles() {
        Set<Map.Entry<String, BundleData>> entries = BundleStorage.getBundles().entrySet();
        for (Map.Entry<String, BundleData> entry : entries) {
            BundleData bundleData = entry.getValue();
            // 获取BundleProxy对象并存储到mProxies中
            Object bundle = reflexClass(bundleData.getBundleClassName());
            if (bundle != null && bundle instanceof IBundle) {
                BundleProxy proxy = new BundleProxy.Builder().setiBundle((IBundle) bundle)
                        .setInfo(bundleData).build();
                mProxies.add(proxy);
                Log.i(TAG, "loadBundles: " + bundleData.getBundleClassName());
            }
        }
    }

    /**
     * 加载bundleInfo
     * @param className
     */
    private void initBundles(String className) {
        Object bundleInit = reflexClass(className);
        if (bundleInit == null){
            return;
        }
        Log.i(TAG, "initBundles: " + className);
        if (bundleInit instanceof IBundleInit){
            // 调用将bundledata存储到bundleStroage中
            ((IBundleInit) bundleInit).init();
        }
    }

    private Object reflexClass(String className) {
        try {
            Class<?> aClass = Class.forName(className);
            try {
                return aClass.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private IBundle getIBundle(String className) {
        return null;
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
