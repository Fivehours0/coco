package com.cococloud.security.annotation;

import java.lang.annotation.*;

/**
 * @author dzh
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecurityIgnoreUrl {

    /**
     * @return 是否由切面进行处理
     */
    boolean value() default true;
}
