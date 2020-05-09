package com.nullok.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.Map;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 16:21
 */
public class ParseRequestHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 判断是不是HTTP request请求
        if (msg instanceof FullHttpRequest) {
            System.out.println("类型 = " + msg.getClass());
            System.out.println("客户地址 = " + ctx.channel().remoteAddress());
            FullHttpRequest request = (FullHttpRequest) msg;
            HttpMethod method = request.method();
            System.out.println("请求方式" + method.name());

            String s = request.content().toString(CharsetUtil.UTF_8);
            System.out.println(s);

            String uri = request.uri();
            System.out.println("uri=> "+uri);
            String name = ctx.name();
            System.out.println("name=" + name);
            HttpHeaders headers = request.headers();
            for (Map.Entry<String, String> entry : request.headers()) {
                System.out.println(entry);
            }


        }

        // 回复信息给浏览器
        ByteBuf buf = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);

        // 构造一个http response
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());

        // response 返回
        ctx.writeAndFlush(response);
    }
}
