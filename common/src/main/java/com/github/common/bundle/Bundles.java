package com.github.common.bundle;

import android.util.Log;

import com.github.annotation.bundle.BundleConstains;
import com.github.annotation.bundle.BundleData;
import com.github.annotation.bundle.BundleStorage;
import com.github.annotation.bundle.IBundleInit;
import com.github.annotation.router.IRouterInit;
import com.github.annotation.router.RouterConstains;
import com.github.annotation.service.IBundleService;
import com.github.annotation.service.ServiceConstances;
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
                    proxy1.getBundelData().getPropery().intValue() - proxy2.getBundelData().getPropery().intValue());

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
                // router初始化
                if (className.startsWith(RouterConstains.PACKAGE_NAME)
                    && className.endsWith(RouterConstains.CLASS_NAME_SEPARATOR.concat(RouterConstains.ROUTER_NAME_SUFIX))){
                    initRouter(className);
                }
                // 读取所有BundleInit类
                if (className.startsWith(BundleConstains.PACKAGE_NAME) &&
                        className.endsWith(BundleConstains.CLASS_NAME_SEPARATOR.concat(BundleConstains.CLASS_NAME_SUFIX))){
                    // 1 解析组件 存储到bundleStroage中
                    initBundles(className);
                }

                // BundleService初始化
                if (className.endsWith(ServiceConstances.CLASS_NAME_SEPARATOR.concat(ServiceConstances.CLASS_NAME_SUFIX))
                        && className.startsWith(ServiceConstances.PACKAGE_NAME)){
                    initService(className);
                }
            }
            // 2 加载组件
            loadBundles();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initService(String className) {
        Object routerServiceRegister = reflexClass(className);
        if (routerServiceRegister == null){
            return;
        }
        Log.i(TAG, "initService: " + className);
        if (routerServiceRegister instanceof IBundleService){
            ((IBundleService) routerServiceRegister).regist();
        }
    }

    // 加载router
    private void initRouter(String className) {
        Object routerInit = reflexClass(className);
        if (routerInit == null){
            return;
        }
        Log.i(TAG, "initRouter: " + className);
        if (routerInit instanceof IRouterInit){
            ((IRouterInit) routerInit).init();
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
