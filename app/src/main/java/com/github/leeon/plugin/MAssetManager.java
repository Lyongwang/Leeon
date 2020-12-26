package com.github.leeon.plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Lyongwang on 2020/11/28 17: 19.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class MAssetManager {
    /**
     * 创建插件资源管理器
     * @param dexPath 插件dex路径
     * @return
     */
    public static AssetManager createAssetManager(String dexPath){
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            final Method addAsssetPath = assetManager.getClass().getMethod("addAsssetPath", String.class);
            addAsssetPath.invoke(assetManager, dexPath);
            return assetManager;
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取带有插件的资源包
     * @param context 壳工程的context
     * @param assetManager 插件的assetManager
     * @return
     */
    public static Resources createResources(Context context, AssetManager assetManager){
        final Resources superResources = context.getResources();
        final Resources resourcesWithPlugin = new Resources(assetManager, superResources.getDisplayMetrics(), superResources.getConfiguration());
        return resourcesWithPlugin;
    }
}
