package com.nullok.model;

import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 路由映射模型
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 12:40
 */
public class RouteMapModel {
    // 根路径
    private String rootPath;
    // controller对应bean
    private Object controller;
    // 方法对应的路径
    private String path;
    // 不同的请求类型存储
    private final Map<Class<? extends Annotation>, Method> map = new HashMap<>();

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<Class<? extends Annotation>, Method> getMap() {
        return map;
    }
}
