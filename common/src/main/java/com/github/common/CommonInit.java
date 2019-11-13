package com.github.common;

import com.github.common.bundle.Bundles;
import com.github.common.base.AppInfo;
import com.github.common.base.BaseApplication;

/**
 * Created by Lyongwang on 2019-11-11 15: 53.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class CommonInit {
    public static void init(BaseApplication application, boolean debug){
        // 保存Application 和 debug
        AppInfo.init(application, debug);
        Bundles.init();
    }
}
