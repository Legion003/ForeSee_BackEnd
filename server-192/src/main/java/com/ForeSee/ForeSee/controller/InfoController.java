package com.ForeSee.ForeSee.controller;

import com.ForeSee.ForeSee.service.InfoService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InfoController {
    @Autowired
    InfoService infoService;

    /**
     * 根据关键词检索内容
     * @param query 关键词
     * @return
     */
    @GetMapping("/companyInfo/{query}")
    @HystrixCommand(fallbackMethod="hystrixWrong")
    public String getCompanyInfo(@PathVariable("query")String query){
        return infoService.getCompanyInfo(query);
    }

    /**
     * 检索某个公司的所有相关信息
     * @param stockCode 公司代码
     * @return
     */
    @GetMapping("/allInfo/{stockCode}")
    public String getAllInfo(@PathVariable("stockCode")String stockCode){
        return infoService.getAllInfo(stockCode);
    }

    public String hystrixWrong(@PathVariable("query")String query){
        return "发生错误";
    }
}

