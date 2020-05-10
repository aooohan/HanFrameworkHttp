package com.nullok.utils;

import com.nullok.annotation.http.mapping.Delete;
import com.nullok.annotation.http.mapping.Get;
import com.nullok.annotation.http.mapping.Post;
import com.nullok.annotation.http.mapping.Put;

import java.lang.annotation.Annotation;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 16:37
 */
public class RequestTypeUtil {

    /**
     * 根据请求名名获取相应的注解class
     * @param type
     * @return
     */
    public static Class<? extends Annotation> stringToClass(String type) {
        switch (type) {
            case "GET":
                return Get.class;
            case "POST":
                return Post.class;
            case "PUT":
                return Put.class;
            case "DELETE":
                return Delete.class;
            default:
                return null;
        }
    }
}
