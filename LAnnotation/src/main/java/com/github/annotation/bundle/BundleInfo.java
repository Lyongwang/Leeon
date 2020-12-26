package com.github.annotation.bundle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Lyongwang
 * @date 2020-02-14 16: 20
 * <p>
 * Email: liyongwang@yiche.com
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface BundleInfo {
    BundlePropery p() default BundlePropery.DEFAULT;

    BundleCode c();

    String v();

    String n();

    String d();

}
