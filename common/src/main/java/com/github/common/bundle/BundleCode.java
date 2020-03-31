package com.github.common.bundle;

/**
 * Created by Lyongwang on 2020-02-14 14: 47.
 * <p>
 * Email: liyongwang@yiche.com
 * 组件的唯一标识
 */
public enum BundleCode {
    PERSONCENTER(10), LOGIN(11);

    private final int code;

    BundleCode(int code) {
        this.code = code;
    }

    public int intValue() {
        return code;
    }
}
