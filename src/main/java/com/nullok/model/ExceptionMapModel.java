package com.nullok.model;

import java.lang.reflect.Method;

/**
 * 异常处理映射模型
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 21:35
 */
public class ExceptionMapModel {
    private final Object instance;
    private final Method method;

    public ExceptionMapModel(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public Object getInstance() {
        return instance;
    }

    public Method getMethod() {
        return method;
    }
}
