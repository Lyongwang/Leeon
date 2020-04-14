package com.github.common.router;

import android.net.Uri;

/**
 * TODO 提取一个router库
 * @author Lyongwang
 * @date 2020/4/13 18: 24
 * <p>
 * Email: liyongwang@yiche.com
 */
public class LRouter {
    public static IRouter buildWithUri(Uri uri){
        return _Router.getInstance().setUri(uri.toString());
    }
}
