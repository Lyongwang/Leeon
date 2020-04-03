package com.github.bundleannotation;

/**
 * Created by Lyongwang on 2020-02-14 14: 50.
 * <p>
 * Email: liyongwang@yiche.com
 * 组件加载的优先级
 */
public enum BundlePropery {
    /** 高优先级*/
    HIGH(3),
    /** 默认优先级*/
    DEFAULT(2),
    /** 低优先级*/
    LOW(1);


    private final int property;

    BundlePropery(int property) {
        this.property = property;
    }

    public int intValue(){
        return property;
    }
}
