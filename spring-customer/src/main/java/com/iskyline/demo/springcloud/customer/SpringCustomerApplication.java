package com.iskyline.demo.springcloud.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCustomerApplication.class,args);
    }

}
