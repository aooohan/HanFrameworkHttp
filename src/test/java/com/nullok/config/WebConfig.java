package com.nullok.config;

import com.nullok.core.config.WebMvcConfigurer;
import com.nullok.core.interceptor.HandlerInterceptor;
import com.nullok.core.interceptor.InterceptorRegistry;
import com.nullok.server.http.HanHttpRequest;
import com.nullok.server.http.HanHttpResponse;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/11 12:41
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HanHttpRequest request, HanHttpResponse response) throws Exception {
                System.out.println("我是拦截器");
                return false;
            }

            @Override
            public void afterCompletion(HanHttpRequest request, HanHttpResponse response) throws Exception {

            }
        }).addPathPatterns("/**", "/aa")
                .excludePathPatterns("/bb");

    }
}
