package com.github.common.bundle;

/**
 * Created by Lyongwang on 2020-02-14 14: 50.
 * <p>
 * Email: liyongwang@yiche.com
 * 组件加载的优先级
 */
public enum BundlePropery {
    HIGH(3), DEFAULT(2), LOW(1);


    private final int property;

    BundlePropery(int property) {
        this.property = property;
    }

    public int intValue(){
        return property;
    }
}
