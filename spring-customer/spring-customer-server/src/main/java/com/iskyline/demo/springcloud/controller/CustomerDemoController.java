package com.iskyline.demo.springcloud.controller;

import com.iskyline.demo.springcloud.feign.UserClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/")
public class CustomerDemoController {

    @Autowired
    private UserClient userClient;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("{userId}/getUserName")
    public String getUserName(@PathVariable("userId") Long userId){
        System.out.println("Customer ServerPort:"+serverPort+",userId="+userId+";"+System.currentTimeMillis());
        return "Customer serverPort:"+serverPort+";"+userClient.getUserName(userId);
    }

}
