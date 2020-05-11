package com.nullok.core.container;

import com.nullok.model.ExceptionMapModel;

import java.lang.reflect.Method;

/**
 * 异常处理 容器映射
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 21:38
 */
public interface ExceptionContainer {

    /**
     * 批量添加
     * @param clazzs
     * @return
     */
    boolean addException(Class<? extends Throwable> [] clazzs, Method method, Object instance);

    /**
     * 向容器中添加映射
     * @param clazz 异常class
     * @param method 异常处理对应的方法
     * @param instance 方法所在类的实例
     * @return
     */
    boolean addException(Class<? extends Throwable> clazz, Method method, Object instance);

    /**
     * 从容器中获取对应的 处理方法
     * @param clazz 异常类class
     * @return ExceptionMapModel
     */
    ExceptionMapModel getException(Class<? extends Throwable> clazz);

    /**
     * 容器中是否存在
     * @param clazz 异常类class
     * @return
     */
    boolean containException(Class<? extends Throwable> clazz);
}
