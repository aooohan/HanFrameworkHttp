package com.nullok.core.context;

import com.nullok.core.container.impl.DefaultRouteContainer;
import com.nullok.core.container.RouteContainer;
import com.nullok.model.ExceptionMapModel;
import com.nullok.utils.PathUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 默认实现
 *
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 11:03
 */
public class DefaultHanApplicationContext extends AnnotationConfigApplicationContext implements HanApplicationContext {

    // 单例用的
    private static DefaultHanApplicationContext context;
    // 路由映射容器
    private final RouteContainer routeContainer = new DefaultRouteContainer();
    // 异常处理映射容器
    private final Map<Class<? extends Throwable>, ExceptionMapModel> exceptionContainer = new HashMap<>();


    @Override
    public boolean addAttr(String rootPath, String path, Class<? extends Annotation> type, Method method, Object controller) {
        return routeContainer.addAttr(
                PathUtil.addSlash(rootPath),
                PathUtil.addSlash(path),
                type,
                method, controller
        );
    }

    @Override
    public Method getAttrMethod(String fullPath, Class<? extends Annotation> type) {
        return routeContainer.getAttrMethod(PathUtil.addSlash(fullPath), type);
    }

    @Override
    public Object getAttrController(String fullPath) {
        return routeContainer.getAttrController(PathUtil.addSlash(fullPath));
    }

    @Override
    public boolean containPathAndType(String fullPath, Class<? extends Annotation> type) {
        return routeContainer.containPathAndType(PathUtil.addSlash(fullPath), type);
    }

    @Override
    public boolean containPath(String fullPath) {
        return routeContainer.containPath(PathUtil.addSlash(fullPath));
    }

    @Override
    public Map<Class<? extends Annotation>, Method> getTypeAndMethodMap(String fullPath) {
        return routeContainer.getTypeAndMethodMap(PathUtil.addSlash(fullPath));
    }

    /**
     * 获取context单例
     *
     * @return context
     */
    public static DefaultHanApplicationContext getContext() {
        // DCL
        if (Objects.isNull(context)) {
            synchronized (DefaultHanApplicationContext.class) {
                if (Objects.isNull(context)) {
                    context = new DefaultHanApplicationContext();
                }
            }
        }
        return context;
    }

    @Override
    public RouteContainer getRouteContainer() {
        return routeContainer;
    }

    @Override
    public boolean addException(Class<? extends Throwable>[] clazzs, Method method, Object instance) {
        if (clazzs.length == 0) {
            return false;
        }
        ExceptionMapModel exceptionMapModel = new ExceptionMapModel(instance, method);
        for (Class<? extends Throwable> clazz : clazzs) {
            if (containException(clazz)) {
                continue;
            }
            exceptionContainer.put(clazz, exceptionMapModel);
        }
        return true;
    }

    @Override
    public boolean addException(Class<? extends Throwable> clazz, Method method, Object instance) {
        if (null == clazz || containException(clazz)) {
            return false;
        }
        ExceptionMapModel exceptionMapModel = new ExceptionMapModel(instance, method);
        exceptionContainer.put(clazz, exceptionMapModel);
        return true;
    }

    /**
     * 遍历容器，根据asSubclass 来判定 clazz 存不存在其本身 或者 父类的映射关系
     * 只能上转型
     * @param clazz 异常类class
     * @return
     */
    @Override
    public ExceptionMapModel getException(Class<? extends Throwable> clazz) {
        for (Map.Entry<Class<? extends Throwable>, ExceptionMapModel> entry : exceptionContainer.entrySet()) {
            Class<? extends Throwable> key = entry.getKey();
            try {
                clazz.asSubclass(key);
            } catch (ClassCastException e) {
                continue;
            }
            return entry.getValue();
        }
        return null;
    }

    @Override
    public boolean containException(Class<? extends Throwable> clazz) {
        return exceptionContainer.containsKey(clazz);
    }
}
