package com.github.leeon;

import android.content.Context;

import com.github.common.CommonInit;
import com.github.common.base.BaseApplication;

import java.io.File;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by Lyongwang on 2019-11-11 13: 59.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class LApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        loadHotFix();
    }

    private void loadHotFix() {
        String fileName = "fix.png";
        final String fixFilePath = getFilesDir().getPath() + File.separator + fileName;
        final File fixFile = new File(fixFilePath);
        if (fixFile.exists()) {
            BaseDexClassLoader classLoader = (BaseDexClassLoader) getClassLoader();
            PathClassLoader fixClassLoader = new PathClassLoader(fixFilePath, null);
            Class loaderClass = BaseDexClassLoader.class;

            try {
                Field pathListField = loaderClass.getDeclaredField("pathList");
                pathListField.setAccessible(true);
                // getClassLoader().pathList
                Object loaderPathList = pathListField.get(classLoader);
                // fixClassLoader.pathList
                final Object fixPathList = pathListField.get(fixClassLoader);

                Class pathListClass = loaderPathList.getClass();

                Field dexElementsField = pathListClass.getField("dexElements");
                dexElementsField.setAccessible(true);
                // fixClassLoader.pathList.dexElements
                Object fixDexElements = dexElementsField.get(fixPathList);
                // 1 全量替换 classLoader.pathList.dexElements = fixClassLoader.pathList.dexElements;
//                dexElementsField.set(loaderPathList, fixDexElements);
                // 2 增加dex
//                classLoader.pathList.dexElements[i+1] = classLoader.pathList.dexElements[i];
//                classLoader.pathList.dexElements[0] = fixClassLoader.pathList.dexElements;

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CommonInit.init(this, BuildConfig.DEBUG);
//        Bundles.getInstance().init();
    }
}
