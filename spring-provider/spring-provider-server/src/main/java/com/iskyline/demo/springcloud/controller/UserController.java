package com.iskyline.demo.springcloud.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UserController {

    @RequestMapping(value = "{userId}/getUserName",method = RequestMethod.GET)
    public String getUserName(@PathVariable("userId") Long userId){
        System.out.println("before:::userId="+userId+";"+System.currentTimeMillis());
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after:::userId="+userId+";"+System.currentTimeMillis());
        return userId+":张三"+System.currentTimeMillis();
    }

}
