package com.iskyline.demo.springcloud.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "spring-provider")
public interface UserClient {

    @RequestMapping(value = "/user/{userId}/getUserName",method = RequestMethod.GET)
    String getUserName(@PathVariable("userId") Long userId);

}
