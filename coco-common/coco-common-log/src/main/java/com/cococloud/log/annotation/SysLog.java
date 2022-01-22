package com.cococloud.log.annotation;

import java.lang.annotation.*;

/**
 * 日志系统注释
 * @author dzh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface SysLog {
    String value() default "";
}
