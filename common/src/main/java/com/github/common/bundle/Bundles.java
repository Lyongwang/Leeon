package com.github.common.bundle;

import com.github.common.base.AppInfo;

/**
 * Created by Lyongwang on 2019-11-11 15: 30.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class Bundles {
    public static void init(){
        BundleManifestParser.parse(AppInfo.getApplication());
    }
}
