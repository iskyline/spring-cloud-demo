package com.iskyline.demo.springcloud.feign.fallback;

import com.iskyline.demo.springcloud.feign.UserClient;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {

    @Override
    public String getUserName(Long userId) {
        return ":::fall back";
    }
}
