package com.nullok.server.http;

import java.lang.annotation.Annotation;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * request对象接口
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 16:56
 */
public interface HanHttpRequest {

    /**
     * 获取body内容
     * @return
     */
    String getContent();

    /**
     * @return 对应的uri
     */
    String getUri();

    /**
     * 获取内容类型
     * @return MIME
     */
    String getContentType();

    /**
     * 从参数中获取值
     * @param name 参数名称
     * @return 对应值
     */
    String getParameter(String name);

    /**
     * 获取所有的参数名
     * @return 所有参数名的枚举集合
     */
    Set<String> getParameterNames();

    /**
     * 获取请求方地址
     * @return 地址
     */
    String getRemoteHost();

    /**
     * 获取请求头
     * @return
     */
    Map<String, String> getHeaders();

    /**
     * 获取请求头
     * @return
     */
    String getHeaders(String key);

    /**
     * 获取请求方式
     *
     * @return
     */
    Class<? extends Annotation> getRequestMethod();

    /**
     * 判断请求参数中是否存在key
     * @param key 键值
     * @return
     */
    boolean containParameterKey(String key);

}
