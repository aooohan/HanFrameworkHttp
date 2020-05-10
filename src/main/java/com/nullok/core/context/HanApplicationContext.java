package com.nullok.core.context;

import com.nullok.core.routeMap.RouteContainer;
import org.springframework.context.ApplicationContext;

/**
 * Han的上下文，全局唯一
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 11:15
 */
public interface HanApplicationContext extends ApplicationContext, RouteContainer {
    /**
     * 获取RouteContainer
     * @return RouteContainer
     */
    RouteContainer getRouteContainer();
}
