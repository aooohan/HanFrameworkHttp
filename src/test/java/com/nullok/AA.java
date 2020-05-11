package com.nullok;

import com.nullok.exception.BeanException;
import com.nullok.exception.RouteException;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 18:55
 */
@Service
@Data
public class AA {
    private int a = 1123;
    private String b = "123";

    public Object a() {
        throw new BeanException("123123");
    }

    public Object b() {
        throw new RuntimeException("123123");
    }
}
