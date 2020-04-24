package com.iskyline.demo.springcloud.controller;

import com.iskyline.demo.springcloud.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/")
public class CustomerDemoController {

    @Autowired
    private UserClient userClient;

    @GetMapping("{userId}/getUserName")
    public String getUserName(@PathVariable("userId") Long userId){
        return userClient.getUserName(userId);
    }

}
