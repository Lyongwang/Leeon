package com.github.annotation.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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

    public <E> E call(Object classInstance, Map<String, Object> params) {
        try {
            method.setAccessible(true);
            Object[] paramValues = getParamValues(params, method.getParameters());
            E methodReturnValue = (E)method.invoke(classInstance, paramValues);
            method.setAccessible(false);
            return methodReturnValue;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(String.format("method %s in service %s params error, please check it!", method.getName(), className));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(String.format("method %s in service %s params error, please check it!", method.getName(), className));
        }
    }

    private Object[] getParamValues(Map<String, Object> params, Parameter[] parameters) {
        if (parameters != null && parameters.length > 0) {
            String[] paramNames = new String[parameters.length];
            int i = 0;
            for (Parameter parameter : parameters) {
                ServiceParam[] paramAnnotation = parameter.getDeclaredAnnotationsByType(ServiceParam.class);
                if (paramAnnotation != null && paramAnnotation.length > 0) {
                    paramNames[i++] = paramAnnotation[0].value();
                }
            }
            int j = 0;
            Object[] paramValues = new Object[paramNames.length];
            for (String paramName : paramNames) {
                paramValues[j++] = params.get(params.get(paramName));
            }
            return paramValues;
        } else {
            return null;
        }
    }
}
