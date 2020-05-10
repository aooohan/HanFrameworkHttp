package com.nullok.server.handler;

import com.nullok.annotation.enums.ContentType;
import com.nullok.core.context.DefaultHanApplicationContext;
import com.nullok.core.context.HanApplicationContext;
import com.nullok.core.resolve.HandleMethodArgumentResolver;
import com.nullok.core.container.RouteContainer;
import com.nullok.server.http.impl.DefaultHanHttpResponse;
import com.nullok.server.http.HanHttpRequest;
import com.nullok.server.http.HanHttpResponse;
import com.nullok.utils.PathUtil;
import com.nullok.utils.ResponseUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 15:36
 */
public class DispatcherRequestHandler extends SimpleChannelInboundHandler<HanHttpRequest> {

    private final HanApplicationContext context = DefaultHanApplicationContext.getContext();
    private final RouteContainer routeContainer = context.getRouteContainer();
    private static Logger logger = LogManager.getLogger(DispatcherRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HanHttpRequest msg) throws Exception {
        String uri = msg.getUri();
        logger.info("[{}] => {}", msg.getRequestMethod(), uri);

        String trimUri = PathUtil.trimUri(uri);
        System.out.println(trimUri);

        Method method = routeContainer.getAttrMethod(trimUri, msg.getRequestMethod());
        Object controller = routeContainer.getAttrController(trimUri);
        HanHttpResponse response1 = new DefaultHanHttpResponse();

        // todo session 未设置

        if (null == method) {
            response1.setHttpStatus(HttpResponseStatus.NOT_FOUND);
            DefaultFullHttpResponse response = ResponseUtil.constructResponse(HttpResponseStatus.NOT_FOUND, uri + "路径不存在", ContentType.PLAIN);
            ctx.writeAndFlush(response);
        } else {
            // 调用method
            try {
                Object result = invokeControllerMethod(method, controller, msg, response1);
            } catch (InvocationTargetException e) {
                handleException(e.getTargetException());
            }
        }

        // 回复信息给浏览器
        ByteBuf buf = Unpooled.copiedBuffer(msg.getContent(), CharsetUtil.UTF_8);

        // 构造一个http response
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());

        // response 返回
        ctx.writeAndFlush(response);
    }

    private void handleException(Throwable throwable) {
    }

    /**
     * 调用uri对应的方法
     * @param method
     * @param controller
     * @param request
     * @param response
     * @return  方法返回值
     */
    private Object invokeControllerMethod(Method method, Object controller, HanHttpRequest request, HanHttpResponse response) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        HandleMethodArgumentResolver resolver = new HandleMethodArgumentResolver(request, method, controller, response);
        List<Object> params = resolver.resolveParams();
        return method.invoke(controller, params.toArray());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage(),cause);
        DefaultFullHttpResponse response = ResponseUtil.constructResponse(
                HttpResponseStatus.INTERNAL_SERVER_ERROR,
                cause.getMessage(),
                ContentType.PLAIN
        );
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
