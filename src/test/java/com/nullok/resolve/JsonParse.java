package com.nullok.resolve;

import com.alibaba.fastjson.JSON;
import com.nullok.core.resolve.ParseObjectResolver;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/11 11:11
 */
@Configuration
public class JsonParse implements ParseObjectResolver {
    @Override
    public Object parseJsonToObject(String json, Class<?> clazz) {
        return JSON.parseObject(json,clazz);
    }

    @Override
    public String parseObjectToJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
