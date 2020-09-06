package com.github.annotation.service;

import com.github.annotation.TextUtils;
import com.github.annotation.router.RouterTable;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    private Map<String, MethodInfo> methodCache = new HashMap<>();

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
        MethodInfo serviceMethod;
        String methodCacheKey = getMethodKey(serviceSchema, methodName);
        if (methodCache.containsKey(methodCacheKey)) {
            serviceMethod = methodCache.get(methodCacheKey);
        } else {
            serviceMethod = getServiceMethod(serviceSchema, methodName, params);
        }
        Object classInstance;
        try {
            classInstance = getClass(Class.forName(serviceMethod.getClassName()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("class %s not found, please check it!", serviceMethod.getClassName()));
        }
        return serviceMethod.call(classInstance, params);
    }

    private String getMethodKey(String serviceSchema, String methodName) {
        return serviceSchema.concat(File.separator).concat(methodName);
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
     * @param params
     * @return
     */
    private MethodInfo getServiceMethod(String serviceSchema, String methodName, Map<String, Object> params) {
        MethodInfo method = RouterTable.getMethod(serviceSchema, methodName);
        if (method == null){
            throw new RuntimeException(String.format("method %s in service %s is not exist, please check it!", methodName, serviceSchema));
        }
        method.setMethod(generateMethod(serviceSchema, method, params));

        return method;

    }

    /**
     * TOTO 缓存
     * 遍历指定服务的注解方法
     * @param serviceName 服务名称
     * @param methodInfo 方法对象
     * @param params
     * @return
     */
    private Method generateMethod(String serviceName, MethodInfo methodInfo, Map<String, Object> params) {
        try {
            Class<?> aClass = Class.forName(methodInfo.getClassName());
            Method[] declaredMethods = aClass.getDeclaredMethods();
            int i = 1;
            for (Method method : declaredMethods){
                boolean isLast = i++ == declaredMethods.length;
                if (matchMethod(methodInfo, method, params)){
                    return method;
                }
            }
            throw new RuntimeException(String.format("method %s in service %s with params \n%s is not exist, please check it!"
                    , methodInfo.getMethodName(), serviceName, getParamsStr(params)));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("service %s is not exist, please check it!", serviceName));
        }
    }

    private String getParamsStr(Map<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        for (Map.Entry<String, Object> entry : entries){
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        return stringBuilder.toString();
    }

    // 找到要反射的方法  方法名称相同 参数个数相同  参数相同  参数类型相同
    private boolean matchMethod(MethodInfo methodInfo, Method method, Map<String, Object> params) {
        // 方法名称相同 & 参数个数相同
        String methodName = method.getName();
        boolean matchName = methodName.equals(methodInfo.getMethodName()) && method.getParameterCount() == params.size();
        if (!matchName){
            return false;
        }
        if (method.getParameterCount() == 0) {
            return true;
        }
        for (Parameter parameter : method.getParameters()) {
            ServiceParam paramAnnotation = parameter.getDeclaredAnnotation(ServiceParam.class);
            if (paramAnnotation == null) {
                return false;
            }
            String paramKey = paramAnnotation.value();
            if (TextUtils.isEmpty(paramKey)){
                return false;
            }
            Object paramValue = params.get(paramKey);
            // 参数相同
            if(paramValue == null
                    // 参数类型相同
                    || !MethodInfo.isSameType(parameter, paramValue)) {
                return false;
            }
        }
        return true;
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
