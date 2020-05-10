package com.nullok.register;

import com.nullok.core.context.DefaultHanApplicationContext;
import com.nullok.core.context.HanApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 注册功能抽象类
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 21:49
 */
public abstract class AbstractRegister {
    protected final HanApplicationContext context = DefaultHanApplicationContext.getContext();
    protected Class<? extends Annotation> annotationClazz;
    protected Logger logger = LogManager.getLogger(this.getClass());

    public AbstractRegister(Class<? extends Annotation> annotationClazz) {
        this.annotationClazz = annotationClazz;
    }

    /**
     * 根据clazz 从IOC容器中获取所有组件
     */
    public void handle() {
        Map<String, Object> controllers = context.getBeansWithAnnotation(annotationClazz);
        for (Map.Entry<String, Object> entry : controllers.entrySet()) {
            Object instance = entry.getValue();
            Class<?> instanceClass = instance.getClass();
            doHandle(instance, instanceClass);
        }
    }

    /**
     * 对组件做相应处理
     * @param instance
     * @param clazz
     */
    protected abstract void doHandle(Object instance, Class<?> clazz);

}
