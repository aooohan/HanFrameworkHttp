package com.nullok.core.container;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 9:30
 */
public interface RouteContainer {
    /**
     * 添加路由映射
     *
     * @param path   路径
     * @param type   请求类型
     * @param method 对应方法
     * @return 是否添加成功
     */
    boolean addAttr(String rootPath, String path, Class<? extends Annotation> type, Method method, Object controller);

    /**
     * 获取对应方法
     * @param fullPath 路径
     * @param type 请求类型
     * @return 方法
     */
    Method getAttrMethod(String fullPath, Class<? extends Annotation> type);

    /**
     * 获取路径对应的controller实例
     * @param fullPath
     * @return
     */
    Object getAttrController(String fullPath);

    /**
     * 根据路径和类型 判断是否已添加
     * @param fullPath 路径
     * @param type 请求类型
     * @return
     */
    boolean containPathAndType(String fullPath, Class<? extends Annotation> type);

    /**
     * 路由是否存在
     * @param fullPath 路径
     * @return
     */
    boolean containPath(String fullPath);

    /**
     * 根据全路径 获取到 请求类型与method 的map
     * @param fullPath  全路径
     * @return 映射集合
     */
    Map<Class<? extends Annotation>, Method> getTypeAndMethodMap(String fullPath);






}
