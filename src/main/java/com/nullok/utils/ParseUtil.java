package com.nullok.utils;

import com.nullok.core.context.DefaultHanApplicationContext;
import com.nullok.core.context.HanApplicationContext;
import com.nullok.core.resolve.ParseObjectResolver;
import com.nullok.core.resolve.impl.HanParseObjectResolver;

import java.util.Map;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/11 10:49
 */
public class ParseUtil {
    private static final HanApplicationContext context = DefaultHanApplicationContext.getContext();
    private static final ParseObjectResolver resolver;
    static {
        Map<String, ParseObjectResolver> beanMap = context.getBeansOfType(ParseObjectResolver.class);
        String[] resolverName = context.getBeanNamesForType(ParseObjectResolver.class);
        if (resolverName.length > 0) {
            Object bean = context.getBean(resolverName[0]);
            resolver = (ParseObjectResolver) bean;
        } else {
            resolver = new HanParseObjectResolver();
        }
    }

    /**
     * 若 实现了ParseObjectResolver 就是调用，否则使用默认
     * @param json
     * @param clazz
     * @return
     */
    public static Object parse(String json,Class<?> clazz) {
        return resolver.parseJsonToObject(json, clazz);
    }
    public static String stringify(Object obj) {
        return resolver.parseObjectToJson(obj);
    }
}
