package com.github.annotation.bundle;

/**
 * Created by Lyongwang on 2020-02-14 14: 47.
 * <p>
 * Email: liyongwang@yiche.com
 * 组件的唯一标识
 */
public enum BundleCode {
    /**common模块*/
    COMMON(1),
    /**APP壳*/
    APP(2),
    /**个人中心模块*/
    PERSONCENTER(10),
    /**登录模块*/
    LOGIN(11);

    private final int code;

    BundleCode(int code) {
        this.code = code;
    }

    public int intValue() {
        return code;
    }
}
