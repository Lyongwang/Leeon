package com.github.common.router;

import android.app.Activity;

import com.github.annotation.router.IParamInjector;
import com.github.annotation.router.RouterConstains;
import com.github.common.base.BaseActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Lyongwang on 2020/4/15 16: 48.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class ParamInjector {

    public static void inject(Activity activity) {
        String clsName = activity.getClass().getCanonicalName();
        try {
            Class clazz = Class.forName(clsName
                    .concat(RouterConstains.CLASS_NAME_SEPARATOR)
                    .concat(RouterConstains.PARAM_NAME_SUFIX));
            Constructor constructorMethod = clazz.getDeclaredConstructor(activity.getClass());
            constructorMethod.setAccessible(true);
            Object object = constructorMethod.newInstance(activity);

            if (object instanceof IParamInjector){
                ((IParamInjector) object).inject();
            }
            constructorMethod.setAccessible(false);
        } catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
