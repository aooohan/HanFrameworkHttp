package com.nullok.core.context;

import com.nullok.core.routeMap.DefaultRouteContainer;
import com.nullok.core.routeMap.RouteContainer;
import com.nullok.utils.PathUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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

    private static DefaultHanApplicationContext context;
    private final RouteContainer routeContainer = new DefaultRouteContainer();

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
}
