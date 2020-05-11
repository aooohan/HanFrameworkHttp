package com.nullok.core.resolve;

/**
 * 全局JSON 处理方式
 * 实现此接口并导入容器即可替换
 * @author ：lihan
 * @description：
 * @date ：2020/5/11 10:41
 */
public interface ParseObjectResolver {

    /**
     * json转object
     * @param json
     * @return
     */
    Object parseJsonToObject(String json,Class<?> clazz);

    /**
     * object转json
     * @param obj
     * @return
     */
    String parseObjectToJson(Object obj);
}
