package com.iskyline.demo.springcloud.feign;

import com.iskyline.demo.springcloud.feign.fallback.UserClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "spring-provider",fallback = UserClientFallback.class)
public interface UserClient {

    @GetMapping(value = "/user/{userId}/getUserName")
    String getUserName(@PathVariable("userId") Long userId);

}
