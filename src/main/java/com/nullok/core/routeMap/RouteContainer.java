package com.nullok.core.routeMap;

import com.nullok.core.beans.BeanContainer;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 路由与method 的映射 容器
 * 全局唯一
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 20:38
 */
public class RouteContainer {
    private static volatile RouteContainer routeContainer;
    private final Map<String, Method> RouteMap = new ConcurrentHashMap<>();

    /**
     * 单例模式，全局唯一，做为Bean容器
     * @return 容器
     */
    public static RouteContainer getBeanContainer() {
        // DCL
        if (Objects.isNull(routeContainer)) {
            synchronized (BeanContainer.class) {
                if (Objects.isNull(routeContainer)) {
                    routeContainer = new RouteContainer();
                }
            }
        }
        return routeContainer;
    }

    /**
     * 从容器中删除指定Bean
     * @param route
     */
    public void removeRoute(String route) {
        RouteMap.remove(route);
    }

    /**
     * 向容器中添加Bean
     * @param route
     * @param method
     */
    public void addRoute(String route, Method method) {
        RouteMap.put(route, method);
    }

    /**
     * 从容器中获取bean
     * @param route
     * @return
     */
    public Object getRoute(String route) {
        return RouteMap.get(route);
    }
}
