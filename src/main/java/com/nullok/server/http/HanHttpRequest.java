package com.nullok.server.http;

import java.util.Enumeration;

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
    Enumeration<String> getParameterNames();

    /**
     * 获取请求方地址
     * @return 地址
     */
    String getRemoteHost();

}
