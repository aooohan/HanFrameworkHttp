package com.nullok.server.http.impl;

import com.nullok.annotation.enums.ContentType;
import com.nullok.server.http.HanHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 17:00
 */
public class DefaultHanHttpResponse implements HanHttpResponse {
    private ContentType contentType;
    private HttpResponseStatus status;
    private HttpHeaders headers;
    private String content;

    public DefaultHanHttpResponse() {
        status = HttpResponseStatus.OK;
        contentType = ContentType.JSON;
    }

    public DefaultHanHttpResponse(ContentType contentType, HttpResponseStatus status, String content) {
        this.contentType = contentType;
        this.status = status;
        this.content = content;
    }

    @Override
    public String getResponseContentType() {
        return contentType.value();
    }

    @Override
    public void setResponseContentType(ContentType type) {
        this.contentType = type;
    }

    @Override
    public HttpResponseStatus getStatus() {
        return status;
    }

    @Override
    public void parseToResponse() {

    }

    @Override
    public void setHeader(String key, String value) {
        headers.set(key, value);
    }

    @Override
    public void setHttpStatus(HttpResponseStatus status) {
        this.status = status;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return this.content;
    }
}
