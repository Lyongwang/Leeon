package com.github.annotation.router;

import java.util.HashMap;

/**
 * Created by Lyongwang on 2020/4/8 18: 26.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class RouterTable {
    private static HashMap<String, Class<?>> schemes = new HashMap<>();

    private static HashMap<String, MethodInfo> servicesMethod = new HashMap<>();

    public static void put(String scheme, Class<?> clazz){
        schemes.put(scheme, clazz);
    }

    public static Class<?> get(String scheme){
        return schemes.get(scheme);
    }
}
