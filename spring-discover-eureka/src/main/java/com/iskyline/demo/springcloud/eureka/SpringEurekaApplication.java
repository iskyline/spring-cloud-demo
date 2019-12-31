package com.iskyline.demo.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.Arrays;

@EnableEurekaServer
@SpringBootApplication
public class SpringEurekaApplication {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        SpringApplication.run(SpringEurekaApplication.class,args);
    }

}
