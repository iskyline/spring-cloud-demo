package com.iskyline.demo.springcloud.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class SpringCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCustomerApplication.class,args);
    }

    /**
     * FIXME 只有使用 restTemplate 调用服务，地址形如 http://spring-provider/provider/hello?msg=%s 时才能加注解 @LoadBalanced
     * 否则会出现错误：java.lang.IllegalStateException: No instances available for lun-a-p1.xxx.com.cn
     * 或者错误： java.lang.IllegalStateException: No instances available for localhost
     * 或者错误：java.net.UnknownHostException: xxxxxxxx
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
