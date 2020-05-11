package com.nullok.core.resolve.impl;

import com.alibaba.fastjson.JSON;
import com.nullok.core.resolve.ParseObjectResolver;

/**
 * 默认全局JSON 解析器
 * @author ：lihan
 * @description：
 * @date ：2020/5/11 10:45
 */
public class HanParseObjectResolver implements ParseObjectResolver {
    @Override
    public Object parseJsonToObject(String json,Class<?> clazz) {
        return JSON.parseObject(json, clazz);
    }

    @Override
    public String parseObjectToJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
