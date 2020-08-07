package com.github.annotation.service;

import com.github.annotation.TextUtils;
import com.github.annotation.router.RouterTable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lyongwang on 2020/4/29 18: 06.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class ServiceCenter {
    /**
     * 缓存服务类实例对象
     */
    private Map<String, Object> classCache = new HashMap<>();

    /**
     * 缓存服务方法对象
     */
    private Map<String, Object> methodCache = new HashMap<>();

    private ServiceCenter(){
    }

    private static class SingletonHolder{
        private static final ServiceCenter INSTANCE = new ServiceCenter();
    }

    static ServiceCenter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    <E> E callMethod(String serviceSchema, String methodName, Map<String, Object> params) {
        checkValid(serviceSchema, methodName);
        MethodInfo serviceMethod = getServiceMethod(serviceSchema, methodName);

        return serviceMethod.call(params);
    }

    private Object getClass(Class<?> clazz) {
        String name = clazz.getName();
        if (classCache.containsKey(name)){
            return classCache.get(name);
        } else {
            try {
                return clazz.newInstance();
            } catch (InstantiationException|IllegalAccessException e) {
                throw new RuntimeException(String.format("class %s not found, please check it!", name));
            }
        }
    }

    /**
     * 获取服务方法
     * @param serviceSchema 服务对应的schema
     * @param methodName
     * @return
     */
    private MethodInfo getServiceMethod(String serviceSchema, String methodName) {
        MethodInfo method = RouterTable.getMethod(serviceSchema, methodName);
        if (method == null){
            throw new RuntimeException(String.format("method %s in service %s is not exist, please check it!", methodName, serviceSchema));
        }
        method.setMethod(generateMethod(serviceSchema, method));

        return method;

    }

    /**
     * TOTO 缓存
     * 遍历指定服务的注解方法
     * @param serviceName 服务名称
     * @param methodInfo 方法对象
     * @return
     */
    private Method generateMethod(String serviceName, MethodInfo methodInfo) {
        try {
            Class<?> aClass = Class.forName(serviceName);
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method method : declaredMethods){
                if (method.getName().equals(methodInfo.getMethodName())){
                    return method;
                }
            }
            throw new RuntimeException(String.format("method %s in service %s is not exist, please check it!", methodInfo.getMethodName(), serviceName));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("service %s is not exist, please check it!", serviceName));
        }
    }

    private void checkValid(String serviceSchema, String methodName) {
        if (TextUtils.isEmpty(serviceSchema)){
            throw new RuntimeException("service schema is empty, please check it!");
        }

        if (TextUtils.isEmpty(methodName)){
            throw new RuntimeException("service method name is empty, please check it!");
        }
    }
}
