package com.nullok.annotation.beans;

import com.nullok.annotation.enums.ContentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 控制层
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestController {
    // 当前controller 的根路径
    String value() default "";

    ContentType contentType() default ContentType.JSON;

}
