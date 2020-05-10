package com.nullok.server.handler;

import com.nullok.server.http.DefaultHanHttpRequest;
import com.nullok.utils.RequestTypeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;

/**
 * 解析请求 => HanHttpRequest
 *
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 16:21
 */
public class ParseRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static Logger logger = LogManager.getLogger(ParseRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        Class<? extends Annotation> requestType = RequestTypeUtil.stringToClass(msg.method().name());
        if (null == requestType) {
            // 回复信息给浏览器
            ByteBuf buf = Unpooled.copiedBuffer("该类型请求暂不支持", CharsetUtil.UTF_8);

            // 构造一个http response
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.METHOD_NOT_ALLOWED, buf);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
            // response 返回
            ctx.writeAndFlush(response);
            return;
        }

        // 判断是不是HTTP request请求
        DefaultHanHttpRequest request = new DefaultHanHttpRequest();
        try {
            request.parse(msg, ctx.channel().remoteAddress().toString());
        } catch (UnsupportedEncodingException e) {
            logger.error("uri解码失败,{}", msg.uri());
        }
        ctx.fireChannelRead(request);
    }
}
