package com.ForeSee.ForeSee.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin("*")
@Slf4j
public class InfoController {
    @Autowired
    RestTemplate restTemplate;
    @Value("${httpUrl}")
    String httpUrl;

    /**
     * 根据关键词检索内容
     * @param query 关键词
     * @return
     */
    @GetMapping("/companyInfo/{query}")
    public String getCompanyInfo(@PathVariable("query")String query){
        String url = httpUrl+"/companyInfo/"+query;
        String result = restTemplate.getForObject(url, String.class);
        log.info(result);
        return result;
    }

    /**
     * 检索某个公司的所有相关信息
     * @param stockCode 公司代码
     * @return
     */
    @GetMapping("/allInfo/{stockCode}")
    public String getAllInfo(@PathVariable("stockCode")String stockCode){
        String url = httpUrl+"/allInfo/"+stockCode;
        String result = restTemplate.getForObject(url, String.class);
        log.info(result);
        return result;
    }
}
