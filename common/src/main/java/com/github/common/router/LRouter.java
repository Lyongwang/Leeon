package com.github.common.router;

import android.net.Uri;

import com.github.annotation.router.RouterConstains;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * TODO 提取一个router库
 * @author Lyongwang
 * @date 2020/4/13 18: 24
 * <p>
 * Email: liyongwang@yiche.com
 */
public class LRouter {
    public static IRouter buildWithUri(Uri uri){
        String url = getRouteUrl(uri);
        Map<String, Object> params = getParams(uri);
        return _Router.getInstance().setUri(url, params);
    }

    private static Map<String, Object> getParams(Uri uri) {
        Map<String, Object> map = new HashMap<>();
        Set<String> parameterNames = uri.getQueryParameterNames();
        if (parameterNames == null || parameterNames.isEmpty()){
            return map;
        }
        for (String name : parameterNames){
            map.put(name, uri.getQueryParameter(name));
        }
        return map;
    }

    private static String getRouteUrl(Uri uri) {
        return uri.getScheme().concat(RouterConstains.SCHEME_SEPARATOR).concat(uri.getHost()).concat(uri.getPath());
    }
}
