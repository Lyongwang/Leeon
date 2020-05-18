package com.github.annotation.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Lyongwang
 * @date 2020/4/29 15: 55
 * <p>
 * Email: liyongwang@yiche.com
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface ServiceRouter {
    String value() default "";
}
