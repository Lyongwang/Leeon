package com.github.annotation.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
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
            E methodReturnValue;
            if (paramValues != null) {
                methodReturnValue = (E) method.invoke(classInstance, paramValues);
            } else {
                methodReturnValue = (E) method.invoke(classInstance);
            }
            method.setAccessible(false);
            return methodReturnValue;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(String.format("method %s in service %s invoke error, please check it!", method.getName(), className));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(String.format("method %s in service %s params error, please check it!", method.getName(), className));
        }
    }

    private Object[] getParamValues(Object classInstance, Map<String, Object> params, Parameter[] parameters) {
        if (!hasParams(parameters, params)) {
            return null;
        } else {
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
                Object paramValue = params.get(paramName);
                if (paramValue != null && isSameType(parameters[j], paramValue)) {
                    paramValues[j++] = paramValue;
                } else {
                    throw new RuntimeException(String.format("method %s param %s type " +
                                    "error, need %s but give %s",
                            methodName, paramName, parameters[j].getType(), paramValue.getClass()));
                }
            }
            return paramValues;
        }
    }

    private boolean hasParams(Parameter[] parameters, Map<String, Object> params) {
        return parameters != null && parameters.length > 0 && params != null && params.size() > 0;
    }

    public static boolean isSameType(Parameter parameter, Object paramValue) {
        if (parameter == null || paramValue == null){
            return false;
        }
        Class<?> paramValueClass = paramValue.getClass();
        Class<?> parameterType = parameter.getType();
        if (paramValueClass == int.class || paramValueClass == Integer.class){
            return parameterType == int.class || parameterType == Integer.class;
        }
        if (paramValueClass == boolean.class || paramValue == Boolean.class){
            return parameterType == int.class || parameterType == Integer.class;
        }
        return parameterType == paramValueClass;
    }
}
