package com.ForeSee.ForeSee.dao;

import com.ForeSee.ForeSee.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zenglr
 * @ClassName RedisDao
 * @Description
 * @create 2020-10-08-9:17 上午
 */
@Slf4j
@Component
public class RedisDao {

    @Autowired
    JedisUtil jedisUtil;
//    根据stockname得到stockcode
//    public static String getCodeByStockcode(String stockcode)
//    {
//        Jedis jedis = new Jedis("192.168.1.108",6479);
//        jedis.auth("nopassword");
//        jedis.select(1);
//        if(jedis.sismember("stockcode", stockcode))
//            return stockcode;
//        else return null;
//    }
//
//    //根据stockname得到stockcode
//    public static String getCodeByStcokname(String stockname)
//    {
//        Jedis jedis = new Jedis("192.168.1.108",6479);
//        jedis.auth("nopassword");
//        jedis.select(2);
//        if(jedis.exists(stockname))
//            return jedis.get(stockname);
//        else return null;
//    }
//
//    //根据companyname得到stockcode
//    public static String getCodeByCompanyname(String companyname)
//    {
//        Jedis jedis = new Jedis("192.168.1.108",6479);
//        jedis.auth("nopassword");
//        jedis.select(3);
//        if ((jedis.exists(companyname)))
//            return jedis.get(companyname);
//        else return null;
//    }

    /**
     * 一框式检索，根据stockcode、stockname、companyname的检索统一返回stockcode
     * @param query
     * @return stockcode
     */
    public String getStockcode(String query)
    {
        query=query.replaceAll(" ","");
        String stockCode = "";
        Jedis jedis= jedisUtil.getClient();
        jedis.select(2);
        if(jedis.sismember("stockcode",query)){
            log.info("query是stockcode类型，redis返回"+query);
            stockCode = query;
        }else if(jedis.exists(query)){
            stockCode = jedis.get(query);
            log.info("redis使用"+query+"获取到stockcode:"+stockCode);
        }else{
            log.info("redis没有查到stockcode");
        }
        jedis.close();
        return stockCode;

    }


}
