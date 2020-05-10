package com.nullok;

import com.nullok.annotation.HanAnnotationScanner;
import com.nullok.core.context.DefaultHanApplicationContext;
import com.nullok.exception.BaseException;
import com.nullok.register.ExceptionRegister;
import com.nullok.register.RouteRegister;
import com.nullok.server.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * framework 启动器
 *
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 20:32
 */
public class HanStarter {
    private static Logger logger = LogManager.getLogger(HanStarter.class);

    public static void start(Class<?> clazz) {

        if (null == clazz) {
            throw new BaseException("启动类参数不可为空！");
        }

        logger.info("启动器启动中,加载相关配置...");

        logger.info("扫描所有组件...");
        String packagePath = clazz.getPackage().getName();
        DefaultHanApplicationContext context = DefaultHanApplicationContext.getContext();
        context.scan(packagePath);
        context.refresh();
        HanAnnotationScanner hanAnnotationScanner = new HanAnnotationScanner(context);
        hanAnnotationScanner.scan(packagePath);

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


//        Method attr = context.getRouteContainer().getAttrMethod("/root", Post.class);
//        attr.setAccessible(true);
//        Parameter[] parameters = attr.getParameters();
//        for (Parameter parameter : parameters) {
//            System.out.println(parameter.getParameterizedType());
//        }
//        try {
//            attr.invoke(context.getAttrController("/root"), "123");
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

    }

    public static void main(String[] args) {
        HanStarter.start(HanStarter.class);
    }
}
