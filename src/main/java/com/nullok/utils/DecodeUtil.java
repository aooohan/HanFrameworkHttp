package com.nullok.utils;


import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数等解码工具
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 14:28
 */
public class DecodeUtil {

    /**
     * 解析uri 将参数封装成map
     * @param uri
     * @return
     */
    public static Map<String, String> parseParameters(String uri) {
        Map<String, String> res = new HashMap<>();
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        Map<String, List<String>> params = queryStringDecoder.parameters();
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            res.put(entry.getKey(), entry.getValue().get(0));
        }
        return res;
    }
}
