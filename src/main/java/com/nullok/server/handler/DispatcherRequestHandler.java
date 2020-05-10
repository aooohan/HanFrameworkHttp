package com.nullok.server.handler;

import com.nullok.core.context.DefaultHanApplicationContext;
import com.nullok.core.context.HanApplicationContext;
import com.nullok.core.resolve.HandleMethodArgumentResolver;
import com.nullok.core.routeMap.RouteContainer;
import com.nullok.server.http.DefaultHanHttpResponse;
import com.nullok.server.http.HanHttpRequest;
import com.nullok.server.http.HanHttpResponse;
import com.nullok.utils.PathUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
        } else {
            // 调用method
            invokeControllerMethod(method, controller, msg, response1);
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

    /**
     * 调用uri对应的方法
     * @param method
     * @param controller
     * @param request
     * @param response
     * @return  方法返回值
     */
    private Object invokeControllerMethod(Method method, Object controller, HanHttpRequest request, HanHttpResponse response) {
        method.setAccessible(true);
        HandleMethodArgumentResolver resolver = new HandleMethodArgumentResolver(request, method, controller, response);
        List<Object> params = resolver.resolveParams();
        try {
            Object result = method.invoke(controller, params.toArray());
            System.out.println(result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage().toString(),cause);
        ctx.close();
    }
}
