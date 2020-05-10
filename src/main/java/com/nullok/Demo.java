package com.nullok;

import com.nullok.annotation.beans.RestController;
import com.nullok.annotation.http.mapping.Get;
import com.nullok.annotation.http.mapping.Post;
import com.nullok.annotation.http.mapping.Put;
import com.nullok.annotation.http.params.HeaderParam;
import com.nullok.annotation.http.params.Param;
import com.nullok.annotation.http.params.RequestBody;
import com.nullok.server.http.HanHttpRequest;
import com.nullok.server.http.HanHttpResponse;
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
    public Object ss(@Param(value = "a",defaultValue = "1",require = false) Integer a, @HeaderParam("Content-Type") String c, @RequestBody AA aa, HanHttpRequest request, HanHttpResponse response) {
        System.out.println(c);
        System.out.println(a);
        System.out.println(aa);
        System.out.println(request);
        System.out.println(response);
        return new AA();
    }
    @Put
    public void aa() {

    }
    public static void main(String[] args) {
        String str = "/path/{}/pa";
        String[] split = str.split("\\{([^\\}]+)\\}");
        System.out.println(Get.class.getSimpleName());
    }
}
