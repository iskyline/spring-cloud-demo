package com.iskyline.demo.springcloud.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UserController {

    @RequestMapping(value = "{userId}/getUserName",method = RequestMethod.GET)
    public String getUserName(@PathVariable("userId") Long userId){
        return userId+":张三";
    }

}
