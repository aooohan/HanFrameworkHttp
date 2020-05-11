package com.nullok.core.interceptor;

import com.nullok.server.http.HanHttpRequest;
import com.nullok.server.http.HanHttpResponse;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/11 11:29
 */
public interface HandlerInterceptor {

    /**
     * 处理请求前
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    default boolean preHandle(HanHttpRequest request, HanHttpResponse response) throws Exception {
        return true;
    }

    /**
     * 请求完成后
     * @param request
     * @param response
     * @throws Exception
     */
    default void afterCompletion(HanHttpRequest request, HanHttpResponse response) throws Exception {
    }
}
