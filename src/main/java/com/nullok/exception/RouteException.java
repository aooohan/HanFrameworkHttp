package com.nullok.exception;

/**
 * Bean异常
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 19:21
 */
public class RouteException extends RuntimeException {
    public RouteException(String message) {
        super(message);
    }
}
