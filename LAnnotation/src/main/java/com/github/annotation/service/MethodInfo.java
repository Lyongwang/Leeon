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
            Object[] paramValues = getParamValues(classInstance, params, method.getParameters());
            E methodReturnValue = (E)method.invoke(classInstance, paramValues);
            method.setAccessible(false);
            return methodReturnValue;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(String.format("method %s in service %s invoke error, please check it!", method.getName(), className));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(String.format("method %s in service %s params error, please check it!", method.getName(), className));
        }
    }

    private Object[] getParamValues(Object classInstance, Map<String, Object> params, Parameter[] parameters) {
        if (parameters != null && parameters.length > 0) {
            String[] paramNames = new String[parameters.length];
            int i = 0;
            for (Parameter parameter : parameters) {
                ServiceParam paramAnnotation = parameter.getDeclaredAnnotation(ServiceParam.class);
                if (paramAnnotation != null) {
                    paramNames[i++] = paramAnnotation.value();
                } else {
                    throw new RuntimeException(String.format("method <%s> in service <%s> param <%s> no annotation, please check it!"
                            , methodName, classInstance.getClass().getCanonicalName(), parameter.getName()));
                }
            }
            int j = 0;
            Object[] paramValues = new Object[paramNames.length];
            for (String paramName : paramNames) {
                Object paramValue = params.get(params.get(paramName));
                if (parameters[j].getType() == paramValue.getClass()) {
                    paramValues[j++] = paramValue;
                } else {
                    throw new RuntimeException(String.format("method %s param %s type " +
                                    "error, need %s but give %s",
                            methodName, paramName, parameters[j].getType(), paramValue.getClass()));
                }
            }
            return paramValues;
        } else {
            return null;
        }
    }
}