package com.nullok.annotation;

import com.nullok.annotation.beans.RestController;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义注解扫描器
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 10:36
 */
public class HanAnnotationScanner extends ClassPathBeanDefinitionScanner {
    private static List<Class<? extends Annotation>> annotationList;

    static {
        annotationList = Arrays.asList(RestController.class);
    }
    public HanAnnotationScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public int scan(String... basePackages) {
        // 注册自己的注解
        annotationList.forEach(annotation -> addIncludeFilter(new AnnotationTypeFilter(annotation)));
        return super.scan(basePackages);
    }

}
