package com.nullok.utils;

import com.nullok.annotation.enums.ContentType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 20:27
 */
public class ResponseUtil {
    /**
     * 构造FullResponse
     * @param status
     * @param msg
     * @param contentType
     * @return
     */
    public static DefaultFullHttpResponse constructResponse(HttpResponseStatus status, String msg,ContentType contentType) {
        ByteBuf buf = Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8);
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, buf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
        response.headers().set(HttpHeaderNames.DATE, new Date());
        response.headers().set(HttpHeaderNames.SERVER, "hanHttpSever/0.1");
        return response;
    }
}
