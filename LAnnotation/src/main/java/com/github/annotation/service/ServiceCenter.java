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

    <E> E callMethod(String serviceName, String methodName, Map<String, Object> params) {
        checkValid(serviceName, methodName);
        MethodInfo serviceMethod = getServiceMethod(serviceName, methodName, params);
        return callMethod(serviceMethod);
    }

    private <E> E callMethod(MethodInfo serviceMethod) {
        try {
            Class<?> clazz = Class.forName(serviceMethod.getClassName());
            Method declaredMethod = clazz.getDeclaredMethod(serviceMethod.getMethodName(), clazz);
            declaredMethod.setAccessible(true);
            E methodReturnValue = (E)declaredMethod.invoke(getClass(clazz), serviceMethod.getParamValues());
            declaredMethod.setAccessible(false);
            return methodReturnValue;
        } catch (ClassNotFoundException| IllegalAccessException e) {
            throw new RuntimeException(String.format("class %s not found, please check it!", serviceMethod.getClassName()));
        } catch (NoSuchMethodException|InvocationTargetException e) {
            throw new RuntimeException(String.format("method %s in service %s params error, please check it!", serviceMethod.getMethodName(), serviceMethod.getClassName()));
        }
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
     * @param serviceName
     * @param methodName
     * @param params
     * @return
     */
    private MethodInfo getServiceMethod(String serviceName, String methodName, Map<String, Object> params) {
        MethodInfo method = RouterTable.getMethod(serviceName, methodName);
        if (method == null){
            throw new RuntimeException(String.format("method %s in service %s is not exist, please check it!", methodName, serviceName));
        }
        if (!checkParams(params, method)){
            throw new RuntimeException(String.format("method %s in service %s params error, please check it!", methodName, serviceName));
        } else {
            return method;
        }

    }

    /**
     * 对比参数类型是否匹配, 如匹配将参数值保存到参数列表中
     * @param params 传入参数
     * @param methodInfo 注解方法
     * @return
     */
    private boolean checkParams(Map<String, Object> params, MethodInfo methodInfo) {
        List<ParameterInfo> methodParams = methodInfo.getParams();
        if (params.size() != methodParams.size()){
            return false;
        }
        for (ParameterInfo parameterInfo : methodParams){
            Object paramsValue = params.get(parameterInfo.getName());
            if (parameterInfo.getType() != paramsValue.getClass()){
                return false;
            } else {
                methodInfo.addParamValue(paramsValue);
            }
        }
        return true;
    }

    private void checkValid(String serviceName, String methodName) {
        if (TextUtils.isEmpty(serviceName)){
            throw new RuntimeException("service name is empty, please check it!");
        }

        if (TextUtils.isEmpty(methodName)){
            throw new RuntimeException("service method name is empty, please check it!");
        }
    }
}
