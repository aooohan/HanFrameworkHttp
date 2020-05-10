package com.nullok;

import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 18:55
 */
@Service
public class AA {
    public Object a() {
        throw new RuntimeException("123123");
    }
}
