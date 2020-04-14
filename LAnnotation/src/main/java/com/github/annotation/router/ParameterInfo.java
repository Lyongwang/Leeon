package com.github.annotation.router;

import java.lang.reflect.Type;

/**
 * Created by Lyongwang on 2020/4/10 11: 14.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class ParameterInfo {
    // 参数顺序
    private int index;
    // 参数名
    private String name;
    // 参数值
    private String value;
    // 参数类型
    private Type type;

    public ParameterInfo(int index, String name, String value, Type type) {
        this.index = index;
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }
}
