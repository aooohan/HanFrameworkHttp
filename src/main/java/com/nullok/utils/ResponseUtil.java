package com.nullok.utils;

import com.nullok.annotation.enums.ContentType;
import com.nullok.server.http.HanHttpResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.Objects;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 20:27
 */
public class ResponseUtil {

    /**
     * 构造FullResponse
     * @param response
     * @return
     */
    public static DefaultFullHttpResponse constructResponse(HanHttpResponse response) {
        String content = response.getContent();
        ByteBuf buf = Unpooled.copiedBuffer(Objects.isNull(content)?"":content, CharsetUtil.UTF_8);
        DefaultFullHttpResponse fullResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, response.getStatus(), buf);
        fullResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, response.getResponseContentType());
        fullResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
        fullResponse.headers().set(HttpHeaderNames.DATE, new Date());
        fullResponse.headers().set(HttpHeaderNames.SERVER, "hanHttpSever/0.1");
        return fullResponse;
    }
}
