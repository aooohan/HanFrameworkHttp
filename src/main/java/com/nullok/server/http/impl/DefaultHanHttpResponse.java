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

    public DefaultHanHttpResponse() {
        status = HttpResponseStatus.OK;
        contentType = ContentType.JSON;
    }

    @Override
    public ContentType getResponseContentType() {
        return null;
    }

    @Override
    public void setResponseContentType(ContentType type) {

    }

    @Override
    public HttpResponseStatus getStatus() {
        return null;
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
}
