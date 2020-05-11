package com.nullok.exception;

/**
 * Bean异常
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 19:21
 */
public class ConfigException extends RuntimeException {
    public ConfigException(String message) {
        super(message);
    }
}
