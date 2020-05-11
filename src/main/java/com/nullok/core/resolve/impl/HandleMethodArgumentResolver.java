package com.nullok.core.resolve.impl;

import com.alibaba.fastjson.JSON;
import com.nullok.annotation.http.params.HeaderParam;
import com.nullok.annotation.http.params.Param;
import com.nullok.annotation.http.params.RequestBody;
import com.nullok.exception.RouteException;
import com.nullok.server.http.HanHttpRequest;
import com.nullok.server.http.HanHttpResponse;
import com.nullok.utils.ParseUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 解析Method参数
 *
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 17:28
 */
public class HandleMethodArgumentResolver {
    // 请求体
    private final HanHttpRequest request;
    // 对应的method
    private final Method method;
    // 对应的类实例
    private final Object controller;
    // 响应体
    private final HanHttpResponse response;
    // 解析完之后与参数列表对应的值
    private final List<Object> params = new ArrayList<>();


    public HandleMethodArgumentResolver(HanHttpRequest request, Method method, Object controller, HanHttpResponse response) {
        this.request = request;
        this.method = method;
        this.controller = controller;
        this.response = response;
    }

    public List<Object> resolveParams() throws RouteException{
        Parameter[] parameters = method.getParameters();
        Arrays.stream(parameters).forEach(this::handleAnnotation);
        return params;
    }

    /**
     * 专门处理Param注解
     *
     * @param parameter
     */
    public void processParam(Parameter parameter) {
        Param param = parameter.getAnnotation(Param.class);
        String key = param.value();
        boolean require = param.require();
        Class<?> type = parameter.getType();
        String value = request.getParameter(key);

        if (require && StringUtils.isBlank(value)) {
            throw new RouteException(String.format("Query字符串参数[ %s ]值不能为空!", key));
        }

        if (Objects.isNull(value)) {
            params.add(null);
            return;
        }

        // 类型转换 只支持这10种
        if (type.equals(Integer.class) || type.equals(int.class)) {
            params.add(Integer.valueOf(value));
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            params.add(Double.valueOf(value));
        } else if (type.equals(Float.class) || type.equals(float.class)) {
            params.add(Float.valueOf(value));
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            params.add(Long.valueOf(value));
        } else if (type.equals(Short.class) || type.equals(short.class)) {
            params.add(Short.valueOf(value));
        } else {
            throw new RouteException(String.format("参数类型[ %s ]非法，不可转换!", type.getSimpleName()));
        }


    }

    /**
     * 处理参数对应的注解或者HanHttpRequest HanHttpResponse
     *
     * @param parameter
     */
    public void handleAnnotation(Parameter parameter) {
        if (parameter.isAnnotationPresent(Param.class)) {
            processParam(parameter);
        } else if (parameter.isAnnotationPresent(HeaderParam.class)) {
            HeaderParam annotation = parameter.getAnnotation(HeaderParam.class);
            String key = annotation.value();
            String header = request.getHeaders(key);
            params.add(header);
        } else if (parameter.isAnnotationPresent(RequestBody.class)) {
            Class<?> type = parameter.getType();
            String body = request.getContent();
            Object o = ParseUtil.parse(body, type);
            params.add(o);
        } else {
            if (parameter.getType().equals(HanHttpRequest.class)) {
                params.add(request);
            }
            if (parameter.getType().equals(HanHttpResponse.class)) {
                params.add(response);
            }
        }
    }
}
