package com.nullok.register;

import com.nullok.annotation.beans.RestController;
import com.nullok.annotation.http.mapping.Delete;
import com.nullok.annotation.http.mapping.Get;
import com.nullok.annotation.http.mapping.Post;
import com.nullok.annotation.http.mapping.Put;
import com.nullok.core.context.DefaultHanApplicationContext;
import com.nullok.core.context.HanApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 路由注册器
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 18:29
 */
public class RouteRegister extends AbstractRegister {

    public RouteRegister() {
        super(RestController.class);
    }

    /**
     * 处理方法与对象的注解，并注册到routemap
     * @param method 方法
     * @param rootPath 根路径
     * @param controller
     */
    private void doMethodAnnotationHandler(Method method, String rootPath, Object controller) {
        String path = "";
        Class<? extends Annotation> type = null;
        if (method.isAnnotationPresent((type = Get.class))) {
            path = method.getAnnotation(Get.class).value();
        } else if (method.isAnnotationPresent((type = Post.class))) {
            path = method.getAnnotation(Post.class).value();
        } else if (method.isAnnotationPresent((type = Put.class))) {
            path = method.getAnnotation(Put.class).value();
        } else if (method.isAnnotationPresent((type = Delete.class))) {
            path = method.getAnnotation(Delete.class).value();
        } else {
            return;
        }
        if (context.getRouteContainer().addAttr(rootPath, path, type, method, controller)) {
            logger.info("路由=>路径：{}，类型：{}，方法：{}，注册成功...", rootPath + path, type.getSimpleName(), method.getName());
        } else {
            logger.error("路由=>路径：{}，类型：{}，方法：{}，注册失败...", rootPath + path, type.getSimpleName(), method.getName());
        }

    }

    @Override
    protected void doHandle(Object instance, Class<?> clazz) {
        RestController controllerAnnotation = clazz.getAnnotation(RestController.class);
        if (null != controllerAnnotation) {
            String rootPath = controllerAnnotation.value();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                doMethodAnnotationHandler(method,rootPath, instance);
            }
        }
    }
}
