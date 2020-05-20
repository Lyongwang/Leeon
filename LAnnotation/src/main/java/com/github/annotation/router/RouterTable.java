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
        ArrayList<ParameterInfo> params = new ArrayList<>();
        ParameterInfo parameter = new ParameterInfo("key1", String.class);
        ParameterInfo parameter1 = new ParameterInfo("key2", Integer.class);
        params.add(parameter);
        params.add(parameter1);
        method.setParams(params);
        putMethod("leeon://leeon.test/personcenter.personImpl?method=getUserId&key1=value1&key2=value2", method);
    }

    public static void putMethod(String scheme, MethodInfo methodInfo){
        servicesMethod.put(scheme, methodInfo);
    }

    /**
     * 根据服务名称&方法名称 获取服务方法对象,找不到返回空
     * @param serviceName 服务名称
     * @param methodName 方法名称
     * @return
     */
    public static MethodInfo getMethod(String serviceName, String methodName) {
        Set<String> methodScheme = servicesMethod.keySet();
        for (String key : methodScheme){
            if (!TextUtils.isEmpty(key) && key.startsWith(serviceName.concat(RouterConstains.METHOD_PROFIX).concat(methodName))){
                return servicesMethod.get(key);
            }
        }
        return null;
    }
}
