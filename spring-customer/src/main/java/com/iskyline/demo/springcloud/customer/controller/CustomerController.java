package com.iskyline.demo.springcloud.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/customer/")
public class CustomerController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * FIXME：注意，spring-provider后面没有端口号
     * @param msg
     * @return
     */
    @GetMapping("say")
    public String say(@RequestParam(name="msg") String msg){
        String url = String.format("http://spring-provider/provider/hello?msg=%s",msg);
        String result = restTemplate.getForObject(url,String.class);
        return "customer:".concat(result);
    }

}
