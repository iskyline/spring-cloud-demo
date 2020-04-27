package com.iskyline.demo.springcloud;

import com.iskyline.demo.springcloud.common.FeignOkHttpConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(defaultConfiguration = FeignOkHttpConfig.class)
@SpringBootApplication
public class CustomerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServerApplication.class,args);
    }

}
