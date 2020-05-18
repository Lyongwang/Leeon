package com.github.annotation.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lyongwang on 2020/4/8 18: 46.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class MethodInfo {
    private String className;
    private String methodName;
    private List<ParameterInfo>  params = new ArrayList<>();
    private List paramValues = new ArrayList();

    public static MethodInfo obtain(String className, String methodName, ParameterInfo... params){
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setClassName(className);
        methodInfo.setMethodName(methodName);
        methodInfo.setParams(Arrays.asList(params));
        return methodInfo;
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

    public List<ParameterInfo> getParams() {
        return params;
    }

    public void setParams(List<ParameterInfo> params) {
        this.params = params;
    }

    public void addParamValue(Object paramsValue) {
        paramValues.add(paramsValue);
    }

    public List getParamValues() {
        return paramValues;
    }
}
