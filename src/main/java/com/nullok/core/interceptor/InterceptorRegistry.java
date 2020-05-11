package com.nullok.core.interceptor;

import com.nullok.core.context.HanApplicationContext;
import org.springframework.stereotype.Component;

import javax.naming.ConfigurationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：lihan
 * @description：
 * @date ：2020/5/11 11:54
 */
@Component
public class InterceptorRegistry {
    private final List<InterceptorRegistration> registrations = new ArrayList<>();

    public InterceptorRegistration addInterceptor(HandlerInterceptor interceptor) {
        InterceptorRegistration registration = new InterceptorRegistration(interceptor);
        registrations.add(registration);
        return registration;
    }

    public List<InterceptorRegistration> getRegistrations() {
        return registrations;
    }
}
