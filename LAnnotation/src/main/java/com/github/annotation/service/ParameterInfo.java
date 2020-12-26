package com.github.annotation.service;

import java.lang.reflect.Type;

/**
 * Created by Lyongwang on 2020/4/10 11: 14.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class ParameterInfo {
    private Type type;
    // 参数名
    private String name;

    public ParameterInfo(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
