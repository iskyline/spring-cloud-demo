package com.iskyline.demo.springcloud.controller;

import com.iskyline.demo.springcloud.client.GitHubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/github/")
public class GitHubController {

    @Autowired
    private GitHubClient gitHubClient;

    @GetMapping(value = "search/repo")
    public String searchRpo(@RequestParam("str") String str){
        return gitHubClient.searchRepo(str);
    }

}
