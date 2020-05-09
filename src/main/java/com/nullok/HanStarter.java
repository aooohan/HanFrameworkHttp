package com.nullok;

import com.nullok.annotation.enums.RequestMethod;
import com.nullok.annotation.http.RequestMapping;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * framework 启动器
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 20:32
 */
public class HanStarter {
    private static AnnotationConfigApplicationContext context;
    public static void start(Class<?> clazz) {
        String packagePath = clazz.getPackage().getName();

        // 扫描包下所有类，Bean注入
        context = new AnnotationConfigApplicationContext();
        context.scan(packagePath);
        context.refresh();

        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(Controller.class);
        for (Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
            System.out.println();
            Object value = entry.getValue();
            Class<?> aClass = value.getClass();
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                boolean annotationPresent = method.isAnnotationPresent(RequestMapping.class);
                System.out.println(annotationPresent);
            }
        }

    }

    public static void main(String[] args) {
        HanStarter.start(HanStarter.class);
    }
}
