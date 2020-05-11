package com.nullok.core.config;


import com.nullok.core.interceptor.HandlerInterceptor;
import com.nullok.core.interceptor.InterceptorRegistry;
import com.nullok.server.http.HanHttpRequest;
import com.nullok.server.http.HanHttpResponse;

/**
 * 配置类
 * @author ：lihan
 * @description：
 * @date ：2020/5/11 11:27
 */
public interface WebMvcConfigurer {

    /**
     * 添加拦截器
     * @param registry
     */
    default void addInterceptors(InterceptorRegistry registry) {

    }
}
