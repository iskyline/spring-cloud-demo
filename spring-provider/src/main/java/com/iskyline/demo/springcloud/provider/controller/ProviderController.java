package com.iskyline.demo.springcloud.provider.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/provider/")
public class ProviderController {

    @GetMapping("hello")
    public String hello(@RequestParam(name = "msg") String msg){
        System.out.println("param:"+msg);
        return "hello:".concat(msg);
    }

}
