package com.iskyline.demo.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/customer/")
public class CustomerDemoController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("{userId}/getUserName")
    public String getUserName(@PathVariable("userId") Long userId){
        System.out.println("Customer ServerPort:"+serverPort+",userId="+userId+";"+System.currentTimeMillis());
        return "Customer serverPort:"+serverPort+";"+restTemplate.getForObject(String.format("http://spring-provider/user/%d/getUserName",userId),String.class);
    }

}
