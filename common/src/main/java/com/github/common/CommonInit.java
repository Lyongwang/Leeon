package com.github.common;

import com.github.common.base.AppInfo;
import com.github.common.base.BaseApplication;
import com.github.common.bundle.Bundles;

/**
 * Created by Lyongwang on 2019-11-11 15: 53.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class CommonInit {
    public static void init(BaseApplication application, boolean debug){
        // 保存Application 和 debug
        AppInfo.init(application, debug);
        // 加载所有bundle对象
        Bundles.getInstance().init();
        // 启动组件
        Bundles.getInstance().start();

        callTestRouterMethod();
    }

    private static void callTestRouterMethod() {
    }
}
