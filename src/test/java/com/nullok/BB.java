package com.nullok;

import com.nullok.annotation.exception.Catch;
import com.nullok.annotation.exception.ExceptionProcess;
import com.nullok.exception.BeanException;
import com.nullok.exception.RouteException;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/10 22:38
 */
@ExceptionProcess
public class BB {

    @Catch(value = {BeanException.class})
    public Object pi(Throwable throwable) {

        return new AA();
    }
    @Catch(value = { Exception.class})
    public Object co(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return new Cc();
    }
}
