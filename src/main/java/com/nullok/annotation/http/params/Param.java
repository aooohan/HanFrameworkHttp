package com.nullok.annotation.http.params;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    // 参数名
    String value() default "";

    // 默认值
    String defaultValue() default "";

    // 参数是否必须
    boolean require() default true;
}
