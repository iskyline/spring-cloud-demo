package com.iskyline.demo.springcloud;

import com.iskyline.demo.springcloud.config.AvoidScan;
import com.iskyline.demo.springcloud.config.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;


@RibbonClients(value = {
        @RibbonClient(name = "spring-provider", configuration = RibbonConfiguration.class)
})
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,value = {AvoidScan.class})
})
@SpringBootApplication
@EnableEurekaClient
public class CustomerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServerApplication.class,args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
