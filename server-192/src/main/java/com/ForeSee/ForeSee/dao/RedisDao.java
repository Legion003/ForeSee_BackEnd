package com.ForeSee.ForeSee.dao;

import com.ForeSee.ForeSee.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
//import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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
        if(jedis.sismember("stock_code",query)){
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

    /**
     * 使用Jedis模糊查询query
     * @param query
     * @return StockCodeList
     */
    public List<String> getStockCodeList(String query)
    {
        long startTime = System.currentTimeMillis();
        List<String> keys = FuzzySearchQuery(query);
        Jedis jedis= jedisUtil.getClient();
        jedis.select(2);
        log.info("模糊匹配到keys："+keys.toString());
        List<String> list = new ArrayList<>();
        if(keys.size()>0){
            for(String key : keys){
                list.add(jedis.get(key));
            }
        }else {
            log.info("redis没有查到，返回"+list.toString());
            return list;
        }
        List<String> result = new ArrayList<String>(new LinkedHashSet<String>(list)); //去重（顺序不变）
        log.info("redis模糊查找:"+query+",返回"+result.toString());
        long finishQueryTime = System.currentTimeMillis();
        log.info("Jedis process time:" + (finishQueryTime - startTime));
        return result;
    }

    /**
     * 进行模糊匹配
     * @param query
     * @return
     */
    public List<String> FuzzySearchQuery(String query){
        log.info("{} 模糊匹配",query);
        String pattern=query.trim().replaceAll("\\s+","*");
        pattern="*"+pattern+"*";
        List<String>res=jedisScan(pattern);
        log.info("{} 模糊匹配,size:{}",pattern, res.size());
        return res.subList(0,Math.min(10,res.size()));
    }

    /**
     * 使用jedis进行key的扫描匹配
     * @param pattern
     * @return keys
     */
    private List<String> jedisScan(String pattern){
        long startTime = System.currentTimeMillis();
        Jedis jedis= jedisUtil.getClient();
        jedis.select(2);
        String cursor = ScanParams.SCAN_POINTER_START;
        List<String> keys = new ArrayList<>();
        ScanParams scanParams = new ScanParams();
        scanParams.match(pattern);
        scanParams.count(1000);
        while (true){
            //使用scan命令获取数据，使用cursor游标记录位置，下次循环使用
            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
            cursor = scanResult.getCursor();// 返回0 说明遍历完成
            keys = scanResult.getResult();
            if ("0".equals(cursor)){
                break;
            }
        }
        long finishTime = System.currentTimeMillis();
        log.info("jedisScan process time:" + (finishTime - startTime));
        return keys;
    }


}
