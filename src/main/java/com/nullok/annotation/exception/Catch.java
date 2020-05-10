package com.nullok.annotation.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Catch {
    // 通过注解的方法 要处理的异常
    Class<? extends Throwable>[] value() default {};
}
