package com.github.common.bundle;

/**
 * Created by Lyongwang on 2020-02-14 11: 56.
 * <p>
 * Email: liyongwang@yiche.com
 * 组件基类: onCrate方法相当于Application的onCreate方法
 */
public interface IBundle {
    /**
     * 组件初始方法
     */
    void onCreate();

    /**
     * 组件结束方法
     */
    void onDestory();

}
