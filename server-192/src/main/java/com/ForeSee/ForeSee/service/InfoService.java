package com.ForeSee.ForeSee.service;

import com.ForeSee.ForeSee.dao.MongodbDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InfoService {
    @Autowired
    MongodbDao mongodbDao;

    public String getCompanyInfo(String query){
        // redis方法
        String stockCode = null;
        // mongodb方法
        String companyInfo = mongodbDao.getCompanyInfo(stockCode);
        return companyInfo;
    }

    public String getAllInfo(String stockCode){
        StringBuffer sb = new StringBuffer("{\"companyInfo\":");
        String companyInfo = mongodbDao.getCompanyInfo(stockCode);
        companyInfo = companyInfo.substring(1,companyInfo.length()-1);
        sb.append(companyInfo);
        sb.append(",\"news\":");
        sb.append(mongodbDao.getStockNews(stockCode));
        sb.append(",\"notice_info\":");
        sb.append(mongodbDao.getStockNotice(stockCode));
        sb.append("}");
        return sb.toString();

    }
}
