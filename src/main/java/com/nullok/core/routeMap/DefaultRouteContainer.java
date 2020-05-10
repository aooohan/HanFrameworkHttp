package com.nullok.core.routeMap;

import com.nullok.exception.RouteException;
import com.nullok.model.RouteMapModel;
import com.nullok.utils.PathUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 路由与method 的映射 容器
 * 全局唯一
 *
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 20:38
 */
public class DefaultRouteContainer implements RouteContainer {

    /**
     * 路径与请求类型和方法的映射
     * path -> <RequestType,Method>
     */
    private final Map<String, RouteMapModel> RouteMap = new HashMap<>();

    @Override
    public boolean addAttr(String rootPath,String path, Class<? extends Annotation> type, Method method,Object controller){

        String fullPath = rootPath + path;
        // 第一次添加
        if (!this.containPath(fullPath)) {
            RouteMapModel routeMapModel = new RouteMapModel();
            routeMapModel.setController(controller);
            routeMapModel.setPath(path);
            routeMapModel.setRootPath(rootPath);
            routeMapModel.getMap().put(type, method);
            RouteMap.put(fullPath, routeMapModel);
            return true;
        }

        if (this.containPathAndType(path, type)) {
            return false;
        }

        getTypeAndMethodMap(fullPath).put(type, method);
        return true;
    }

    @Override
    public Method getAttrMethod(String fullPath, Class<? extends Annotation> type) {
        if (containPathAndType(fullPath, type)) {
            return getTypeAndMethodMap(fullPath).get(type);
        }
        return null;
    }

    @Override
    public Object getAttrController(String fullPath) {
        return RouteMap.get(fullPath).getController();
    }

    @Override
    public boolean containPathAndType(String fullPath, Class<? extends Annotation> type) {
        return containPath(fullPath) && getTypeAndMethodMap(fullPath).containsKey(type);
    }

    @Override
    public boolean containPath(String fullPath) {
        return StringUtils.isNotBlank(fullPath) && RouteMap.containsKey(fullPath);
    }

    @Override
    public Map<Class<? extends Annotation>, Method> getTypeAndMethodMap(String fullPath) {
        return RouteMap.get(fullPath).getMap();
    }

}
