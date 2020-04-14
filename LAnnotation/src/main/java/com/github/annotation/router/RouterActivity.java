package com.github.annotation.router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Lyongwang
 * @date 2020/4/8 15: 22
 * <p>
 * Email: liyongwang@yiche.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface RouterActivity {
    String scheme() default "harsh.leeon";

    String host() default "leeon.test";

    String[] paths() default {};

    String path();
}
