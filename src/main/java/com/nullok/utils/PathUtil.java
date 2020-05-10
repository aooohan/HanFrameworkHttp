package com.nullok.utils;

import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * 请求路径 工具类
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 12:54
 */
public class PathUtil {

    /**
     * 补全 /
     * @param path
     * @return
     */
    public static String addSlash(String path) {
        if (path.charAt(0) != '/') {
            path = "/" + path;
        }
        return path;
    }

    /**
     * 去掉uri中的参数部分
     * @param uri
     * @return
     */
    public static String trimUri(String uri) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        return queryStringDecoder.path();
    }
}
