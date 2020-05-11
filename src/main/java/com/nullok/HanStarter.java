package com.nullok;

import com.nullok.annotation.HanAnnotationScanner;
import com.nullok.core.config.WebMvcConfigurer;
import com.nullok.core.context.DefaultHanApplicationContext;
import com.nullok.core.interceptor.InterceptorRegistry;
import com.nullok.exception.BaseException;
import com.nullok.register.ExceptionRegister;
import com.nullok.register.RouteRegister;
import com.nullok.server.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

/**
 * framework 启动器
 *
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 20:32
 */
public class HanStarter {
    private static final Logger logger = LogManager.getLogger(HanStarter.class);
    private static DefaultHanApplicationContext context;
    public static void start(Class<?> clazz) {

        if (null == clazz) {
            throw new BaseException("启动类参数不可为空！");
        }

        logger.info("启动器启动中,加载相关配置...");

        logger.info("扫描所有组件...");
        String packagePath = clazz.getPackage().getName();
        context = DefaultHanApplicationContext.getContext();
        context.scan(packagePath);
        context.refresh();
        HanAnnotationScanner hanAnnotationScanner = new HanAnnotationScanner(context);
        hanAnnotationScanner.scan(packagePath);

        // 注册拦截器
        registerInterceptor();

        // 映射全局异常处理
        logger.info("开始注册全局异常处理....");
        ExceptionRegister exceptionRegister = new ExceptionRegister();
        exceptionRegister.handle();

        // 映射路由
        logger.info("开始注册路由....");
        RouteRegister routeRegister = new RouteRegister();
        routeRegister.handle();

        // 启动netty
        logger.info("启动Netty服务器...");
        HttpServer.run();
    }

    private static void registerInterceptor() {
        try {
            // 读取WebMvcConfigure 配置
            WebMvcConfigurer webMvcConfigurer = context.getBean(WebMvcConfigurer.class);
            // 手工注册bean到Spring 容器
//            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(InterceptorRegistry.class);
//            context.registerBeanDefinition(InterceptorRegistry.class.getSimpleName(), beanDefinitionBuilder.getBeanDefinition());
            InterceptorRegistry interceptorRegistry = context.getBean(InterceptorRegistry.class);
            webMvcConfigurer.addInterceptors(interceptorRegistry);
        } catch (NoSuchBeanDefinitionException e) {
            logger.info("未配置webMvcConfigurer...");
        }
    }

}
