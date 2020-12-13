package com.github.leeon.utils;

import android.os.Trace;

import java.util.Collection;

/**
 * Created by Lyongwang on 2020/4/3 18: 26.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class Collections {
    public static boolean isEmpty(Collection collection){
        final boolean b = collection == null || collection.isEmpty();
        return b;
    }
}
