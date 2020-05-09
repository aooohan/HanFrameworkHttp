package com.nullok;

import com.nullok.annotation.beans.Autowired;
import com.nullok.annotation.http.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 21:09
 */
@Controller
public class Demo {
    @Autowired
    private Demo demo;
    @RequestMapping
    public void stat() {

    }
}
