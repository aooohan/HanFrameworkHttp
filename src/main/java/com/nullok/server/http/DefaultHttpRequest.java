package com.nullok.server.http;

import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;

import java.util.Enumeration;

/**
 * 默认的请求体
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 16:30
 */
public class DefaultHttpRequest implements HanHttpRequest {
    private HttpMethod method;
    private HttpHeaders headers;
    private HttpContent content;


    @Override
    public String getContent() {
        return null;
    }

    @Override
    public String getUri() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public String getParameter(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }
}
