package com.github.common.event;

import com.github.common.bundle.IBundle;
import com.github.common.event.eventor.IEventor;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Lyongwang
 * @date 2020/11/22 13: 39
 * <p>
 * Email: liyongwang@yiche.com
 */
public class EventCenter {
    public static Set<IBundle> iBundles = new HashSet<>();

    /**
     * 注册模块
     * @param iBundle
     */
    public static void regist(IBundle iBundle){
        iBundles.add(iBundle);
    }

    /**
     * 注销模块
     * @param iBundle
     */
    public static void unRegist(IBundle iBundle){
        iBundles.remove(iBundle);
    }

    /**
     * 通知信息变更
     * @param iEventor
     */
    public static void event(IEventor iEventor){
        for (IBundle iBundle: iBundles) {
            iBundle.onEvent(iEventor);
        }
    }
}
