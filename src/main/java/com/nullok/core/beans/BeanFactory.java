package com.nullok.core.beans;

import com.nullok.exception.BeanException;

/**
 * bean容器
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 19:19
 */
public interface BeanFactory {

    /**
     * 根据name 获取指定bean
     * @param name bean名称
     * @return
     * @throws BeanException
     */
    Object getBean(String name) throws BeanException;

    /**
     * 根据name和Class 获取指定bean
     * @param name
     * @param type
     * @param <T>
     * @return
     * @throws BeanException
     */
    <T> T getBean(String name, Class<T> type) throws BeanException;

    /**
     * bean是否存在
     * @param name
     * @return
     */
    boolean hasBean(String name);

}
