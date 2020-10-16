package com.ForeSee.ForeSee.controller;

import com.ForeSee.ForeSee.dao.MongodbDao;
import com.ForeSee.ForeSee.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InfoController {
    @Autowired
    InfoService infoService;

    @GetMapping("/companyInfo/{query}")
    public String getCompanyInfo(@PathVariable("query")String query){
        return infoService.getCompanyInfo(query);
    }

    @GetMapping("/allInfo/{stockCode}")
    public String getAllInfo(@PathVariable("stockCode")String stockCode){
        return infoService.getAllInfo(stockCode);
    }
}
