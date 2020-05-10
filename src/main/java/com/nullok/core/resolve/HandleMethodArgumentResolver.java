package com.nullok.core.resolve;

import com.nullok.server.http.HanHttpRequest;
import com.nullok.server.http.HanHttpResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 解析Method参数
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 17:28
 */
public class HandleMethodArgumentResolver {
    // 请求体
    private HanHttpRequest request;
    // 对应的method
    private Method method;
    // 对应的类实例
    private Object controller;
    // 响应体
    private HanHttpResponse response;
    // 解析完之后与参数列表对应的值
    private List<Object> params = new ArrayList<>();

    public HandleMethodArgumentResolver(HanHttpRequest request, Method method, Object controller, HanHttpResponse response) {
        this.request = request;
        this.method = method;
        this.controller = controller;
        this.response = response;
    }

    public void resolveParams() {
        Parameter[] parameters = method.getParameters();
//        Arrays.stream(parameters)
//                .map(parameter -> {
//                }).collect();
    }
}
