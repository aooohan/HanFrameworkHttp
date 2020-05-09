package com.nullok.core.beans;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean容器
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 19:15
 */
public class BeanContainer {
    private static volatile BeanContainer container;
    private final Map<String, Object> BeanMap = new ConcurrentHashMap<>();

    public BeanContainer() {
    }

    /**
     * 单例模式，全局唯一，做为Bean容器
     * @return 容器
     */
    public static BeanContainer getBeanContainer() {
        // DCL
        if (Objects.isNull(container)) {
            synchronized (BeanContainer.class) {
                if (Objects.isNull(container)) {
                    container = new BeanContainer();
                }
            }
        }
        return container;
    }

    /**
     * 从容器中删除指定Bean
     * @param name
     */
    public void removeBean(String name) {
        BeanMap.remove(name);
    }

    /**
     * 向容器中添加Bean
     * @param name
     * @param object
     */
    public void addBean(String name, Object object) {
        BeanMap.put(name, object);
    }

    /**
     * 从容器中获取bean
     * @param name
     * @return
     */
    public Object getBean(String name) {
        return BeanMap.get(name);
    }
}
