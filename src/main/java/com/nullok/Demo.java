package com.nullok;

import com.nullok.annotation.beans.RestController;
import com.nullok.annotation.http.mapping.Get;
import com.nullok.annotation.http.mapping.Post;
import com.nullok.annotation.http.mapping.Put;
import com.nullok.server.http.HanHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/9 21:09
 */
@RestController("/root")
public class Demo {
    @Autowired
    private Demo demo;

    @Get("/stat")
    public void stat() {
    }

    @Post
    public void ss(String a) {
        System.out.println("1111");
        System.out.println(a);
    }
    @Put
    public void aa() {

    }
    public static void main(String[] args) {
        System.out.println(Get.class.getSimpleName());
    }
}
