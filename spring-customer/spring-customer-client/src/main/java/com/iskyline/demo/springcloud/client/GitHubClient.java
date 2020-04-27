package com.iskyline.demo.springcloud.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "github-client",url = "https://api.github/com")
public interface GitHubClient {

    @GetMapping(value = "/search/repositories")
    String searchRepo(@RequestParam("q") String queryStr);

}
