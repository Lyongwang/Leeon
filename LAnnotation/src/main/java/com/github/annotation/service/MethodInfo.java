package com.github.annotation.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Lyongwang on 2020/4/8 18: 46.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class MethodInfo {
    private String              className;
    private String              methodName;
    private Method              method;

    public void setMethod(Method method){
        this.method = method;
    }

    public Method getMethod(){
        return method;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public <E> E call(Map<String, Object> params) {
        try {
            method.setAccessible(true);
            E methodReturnValue = (E)method.invoke(getClass(clazz), params);
            method.setAccessible(false);
            return methodReturnValue;
        } catch (ClassNotFoundException| IllegalAccessException e) {
            throw new RuntimeException(String.format("class %s not found, please check it!", serviceMethod.getClassName()));
        } catch (NoSuchMethodException| InvocationTargetException e) {
            throw new RuntimeException(String.format("method %s in service %s params error, please check it!", serviceMethod.getMethodName(), serviceMethod.getClassName()));
        }
    }
}
