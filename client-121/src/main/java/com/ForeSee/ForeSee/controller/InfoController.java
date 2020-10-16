package com.ForeSee.ForeSee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class InfoController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/companyInfo/{query}")
    public String getCompanyInfo(@PathVariable("query")String query){
        String url = "http://222.200.184.74:8288/companyInfo/"+query;
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/allInfo/{stockCode}")
    public String getAllInfo(@PathVariable("stockCode")String stockCode){
        String url = "http://222.200.184.74:8288/allInfo/"+stockCode;
        return restTemplate.getForObject(url, String.class);
    }
}
