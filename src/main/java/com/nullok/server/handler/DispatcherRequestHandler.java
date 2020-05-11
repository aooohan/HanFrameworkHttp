package com.nullok.server.handler;

import com.nullok.annotation.beans.RestController;
import com.nullok.annotation.enums.ContentType;
import com.nullok.core.context.DefaultHanApplicationContext;
import com.nullok.core.context.HanApplicationContext;
import com.nullok.core.interceptor.HandlerInterceptor;
import com.nullok.core.interceptor.InterceptorRegistration;
import com.nullok.core.interceptor.InterceptorRegistry;
import com.nullok.core.resolve.impl.HandleMethodArgumentResolver;
import com.nullok.core.container.RouteContainer;
import com.nullok.model.ExceptionMapModel;
import com.nullok.server.http.impl.DefaultHanHttpResponse;
import com.nullok.server.http.HanHttpRequest;
import com.nullok.server.http.HanHttpResponse;
import com.nullok.utils.ParseUtil;
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
import java.util.Map;
import java.util.Objects;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 15:36
 */
public class DispatcherRequestHandler extends SimpleChannelInboundHandler<HanHttpRequest> {

    private final HanApplicationContext context;
    private final RouteContainer routeContainer;
    private static final Logger logger = LogManager.getLogger(DispatcherRequestHandler.class);
    private final InterceptorRegistry interceptorRegistry;

    {
        context = DefaultHanApplicationContext.getContext();
        interceptorRegistry = context.getBean(InterceptorRegistry.class);
        routeContainer = context.getRouteContainer();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HanHttpRequest msg) throws Exception {
        String uri = msg.getUri();
        logger.info("[{}] => {}", msg.getRequestMethod(), uri);

        String trimUri = PathUtil.trimUri(uri);
        Method method = routeContainer.getAttrMethod(trimUri, msg.getRequestMethod());
        Object controller = routeContainer.getAttrController(trimUri);
        HanHttpResponse hanResponse = new DefaultHanHttpResponse();
        // todo session 未设置

        if (processInterceptor(trimUri, msg, hanResponse)) {
            if (null == method) {
                hanResponse.setHttpStatus(HttpResponseStatus.NOT_FOUND);
                String info = "<html><head><title>404 NOT FOUND</title></head><body><h2>你请求uri为：" + uri+"该页面不存在</h2></body></html>";
                hanResponse.setContent(info);
            } else {
                Object invokeResult;
                // 调用method
                try {
                    invokeResult = invokeControllerMethod(method, controller, msg, hanResponse);
                } catch (InvocationTargetException e) {
                    invokeResult = handleException(e.getTargetException());
                }
                hanResponse.setContent(ParseUtil.stringify(invokeResult));
            }
        }

        DefaultFullHttpResponse defaultFullHttpResponse = ResponseUtil.constructResponse(hanResponse);
        // response 返回
        ctx.writeAndFlush(defaultFullHttpResponse);



    }

    /**
     * 执行用户配置的拦截器
     */
    private boolean processInterceptor(String uri,HanHttpRequest request, HanHttpResponse response) throws Exception {
        if (interceptorRegistry != null) {
            List<InterceptorRegistration> registrations = interceptorRegistry.getRegistrations();
            for (InterceptorRegistration registration : registrations) {
                List<String> includePatterns = registration.getIncludePatterns();
                List<String> excludePatterns = registration.getExcludePatterns();
                if (includePatterns.contains("/**")) {
                    if (excludePatterns.contains(uri)) {
                        return true;
                    }
                } else if (!includePatterns.contains(uri)) {
                    return true;
                }
                HandlerInterceptor interceptor = registration.getInterceptor();
                boolean result = interceptor.preHandle(request, response);
                if (!result) return false;
            }
        }
        return true;
    }

    /**
     * 全局异常处理
     * @param throwable
     * @return
     * @throws Exception
     */
    private Object handleException(Throwable throwable) throws Exception {
        ExceptionMapModel exceptionMapModel = context.getException(throwable.getClass());
        // 没有设置对应的处理方法，直接抛出 内部异常
        if (Objects.isNull(exceptionMapModel)) {
            throw new RuntimeException();
        } else {
            Method method = exceptionMapModel.getMethod();
            Object instance = exceptionMapModel.getInstance();
            return method.invoke(instance, throwable);
        }

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
        String msg = "<html><head><title>"+"服务器内部错误"+"</title></head><body>" + cause.getMessage() +"</body></html>";
        DefaultHanHttpResponse defaultHanHttpResponse = new DefaultHanHttpResponse(ContentType.HTML, HttpResponseStatus.INTERNAL_SERVER_ERROR, msg);
        DefaultFullHttpResponse defaultFullHttpResponse = ResponseUtil.constructResponse(defaultHanHttpResponse);
        ctx.writeAndFlush(defaultFullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }
}
