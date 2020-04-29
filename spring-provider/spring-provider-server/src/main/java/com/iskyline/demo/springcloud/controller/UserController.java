package com.iskyline.demo.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Value("${server.port:-1}")
    private String port;

    @RequestMapping(value = "{userId}/getUserName",method = RequestMethod.GET)
    public String getUserName(@PathVariable("userId") Long userId, HttpServletRequest request){
        System.out.println("ServerPort:"+port+",port:"+request.getServerPort()+",userId="+userId+";"+System.currentTimeMillis());
        return "ServerPort:"+port+",port:"+request.getServerPort()+","+userId+":张三"+System.currentTimeMillis();
    }

}
