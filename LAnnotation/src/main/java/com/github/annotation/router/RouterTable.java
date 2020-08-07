package com.github.annotation.router;

import com.github.annotation.TextUtils;
import com.github.annotation.service.MethodInfo;
import com.github.annotation.service.ParameterInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Lyongwang on 2020/4/8 18: 26.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class RouterTable {
    /**
     * 存储 scheme-> class 对应关系
     */
    private static HashMap<String, Class<?>> schemes = new HashMap<>();

    public static void put(String scheme, Class<?> clazz){
        schemes.put(scheme, clazz);
    }

    public static Class<?> get(String scheme){
        return schemes.get(scheme);
    }

    /**
     * 存储service -> Method对应关系
     */
    private static HashMap<String, MethodInfo> servicesMethod = new HashMap<>();
    // leeon://leeon.test/personcenter.personImpl?method=getUserId&paramKey=paramValue

    static {
        MethodInfo method = new MethodInfo();
        method.setClassName("com.leeon.personcenter.PersonImpl");
        method.setMethodName("getUserId");
        putMethod("leeon://leeon.test/personcenter.personImpl?method=getUserId", method);
    }

    public static void putMethod(String scheme, MethodInfo methodInfo){
        servicesMethod.put(scheme, methodInfo);
    }

    /**
     * 根据服务名称&方法名称 获取服务方法对象,找不到返回空
     * @param serviceSchema 服务名称
     * @param methodName 方法名称
     * @return
     */
    public static MethodInfo getMethod(String serviceSchema, String methodName) {
        Set<String> methodScheme = servicesMethod.keySet();
        for (String key : methodScheme){
            if (!TextUtils.isEmpty(key) && key.equals(serviceSchema.concat(RouterConstains.METHOD_PROFIX).concat(methodName))){
                return servicesMethod.get(key);
            }
        }
        throw new RuntimeException(throw new RuntimeException(String.format("method %s in service %s is not exist, please check it!", methodName, serviceSchema)););
    }
}
