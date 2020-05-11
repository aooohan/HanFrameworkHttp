package com.nullok.register;


import com.nullok.annotation.exception.Catch;
import com.nullok.annotation.exception.ExceptionProcess;

import java.lang.reflect.Method;

/**
 * 异常处理注册器
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 21:27
 */
public class ExceptionRegister extends AbstractRegister {

    public ExceptionRegister() {
        super(ExceptionProcess.class);
    }

    @Override
    protected void doHandle(Object instance, Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Catch.class)) {
                Catch annotation = method.getAnnotation(Catch.class);
                Class<? extends Throwable>[] exceptions = annotation.value();
                for (Class<? extends Throwable> exception : exceptions) {
                    boolean flag = context.addException(exception, method, instance);
                    if (flag) {
                        logger.info("全局异常处理=>异常：{}，类：{}，方法：{}，注册成功...", exception.getSimpleName(), clazz.getSimpleName(), method.getName());
                    } else {
                        logger.error("全局异常处理=>异常：{}，类：{}，方法：{}，注册失败...", exception.getSimpleName(), clazz.getSimpleName(), method.getName());
                    }
                }

            }
        }
    }
}
