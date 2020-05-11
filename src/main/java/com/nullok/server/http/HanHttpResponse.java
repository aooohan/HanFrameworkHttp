package com.nullok.server.http;

import com.nullok.annotation.enums.ContentType;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 16:52
 */
public interface HanHttpResponse {

    /**
     * 获取响应类型
     * @return ContentType
     */
    String getResponseContentType();

    /**
     * 设置响应类型
     * @param type
     */
    void setResponseContentType(ContentType type);

    /**
     * 获取响应类型
     * @return HttpResponseStatus
     */
    HttpResponseStatus getStatus();

    /**
     * 解析到当前实例
     */
    void parseToResponse();

    /**
     * 设置响应头
     * @param key
     * @param value
     */
    void setHeader(String key, String value);

    /**
     * 设置响应状态
     * @param status
     */
    void setHttpStatus(HttpResponseStatus status);


    /**
     * 设置响应内容
     * @param content
     */
    void setContent(String content);

    /**
     * 获取content
     * @param content
     * @return
     */
    String getContent();



}
