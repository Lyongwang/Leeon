package com.github.annotation.router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Lyongwang
 * @date 2020/4/14 14: 49
 * <p>
 * Email: liyongwang@yiche.com
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface IntentParam {
    String value() default "";
}
