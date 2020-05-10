package com.nullok.annotation.enums;

/**
 * contentType 类型
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 16:55
 */
public enum ContentType {
    JSON("application/json; charset=UTF-8"),
    HTML("text/html; charset=UTF-8"),
    PLAIN("text/plain; charset=UTF-8");

    private String value;

    ContentType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
