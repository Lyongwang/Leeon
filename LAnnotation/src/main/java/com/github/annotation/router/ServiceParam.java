package com.github.annotation.router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Lyongwang
 * @date 2020/4/8 18: 24
 * <p>
 * Email: liyongwang@yiche.com
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface ServiceParam {
    String value();
}
