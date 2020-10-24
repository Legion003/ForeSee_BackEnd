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

    //分页的每一页的结果数
    static int pageRecord = 30;


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
     * 根据传入的query进行切词，返回所有检索字段对应检索结果的stockCode列表
     * @param query
     * @return stockList
     */
    public List<String> getStockCodeList(String query)
    {
        long startTime = System.currentTimeMillis();
        //对检索词串进行切词
        String queries[] = query.split(" ");
        List<String> list = new ArrayList<>();
        for(int i = 0; i < queries.length; i++)
        {
            list.add(queries[i]);
        }
        List<String> res = new ArrayList<>();
        Jedis jedis = jedisUtil.getClient();
        for(String key:list)
        {
            jedis.select(1);
            //如果检索词是stockCode，则不需要做模糊匹配
            if(jedis.sismember("stockCode",key)) {
                res.add(key);
                continue;
            }
            //检索词为文字，进行模糊匹配
            for(int i = 2; i < 5; i++)
            {
                jedis.select(i);
                res.addAll(FuzzySearchList(key,i));
            }
        }
        List<String> result = new ArrayList<String>(new LinkedHashSet<String>(res)); //去重（顺序不变）
        long finishQueryTime = System.currentTimeMillis();
        log.info("Jedis process time:" + (finishQueryTime - startTime));
        jedis.close();
        return result.subList(0,Math.min(10,res.size())); //返回十条
//        return result;  //使用分页查询时使用
    }

    /**
     * 使用Jedis模糊查询query
     * @param query
     * @return result
     */
    public List<String> FuzzySearchList(String query, int baseNum)
    {
        List<String> keys = FuzzySearchQuery(query, baseNum);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(baseNum);
        log.info("在数据库{}中查找",baseNum);
        log.info("模糊匹配到keys："+keys.toString());
        List<String> list = new ArrayList<>();
        if(keys.size()>0){
            if(baseNum == 4){
                for(String key : keys){
                    list.addAll(jedis.smembers(key));
                }
            }
            else
            {
                for (String key : keys) {
                    list.add(jedis.get(key));
                }
            }
        }else {
            log.info("redis没有查到，返回"+list.toString());
            return list;
        }
        log.info("redis模糊查找:"+query+",返回"+list.toString());
        jedis.close();
        return list;
    }

    /**
     * 进行模糊匹配
     * @param query
     * @return
     */
    public List<String> FuzzySearchQuery(String query, int baseNum){
        String pattern=query.trim().replaceAll("\\s+","*");
        pattern="*" + pattern + "*";
        List<String>res=jedisScan(pattern, baseNum);
        log.info("{} 模糊匹配,size:{}",pattern, res.size());
//        return res.subList(0,Math.min(10,res.size()));
        return res;
    }

    /**
     * 使用jedis进行key的扫描匹配
     * @param pattern
     * @return keys
     */
    private List<String> jedisScan(String pattern, int baseNum){
        Jedis jedis= jedisUtil.getClient();
        jedis.select(baseNum);
        String cursor = ScanParams.SCAN_POINTER_START;
        List<String> keys = new ArrayList<>();
        ScanParams scanParams = new ScanParams();
        scanParams.match(pattern);
        scanParams.count(10000);
        while (true){
            //使用scan命令获取数据，使用cursor游标记录位置，下次循环使用
            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
            cursor = scanResult.getCursor();// 返回0 说明遍历完成
            keys = scanResult.getResult();
            if ("0".equals(cursor)){
                break;
            }
        }
        jedis.close();
        return keys;
    }

    /**
     * getAnsOnPage通过传进的页码page和检索词query查找对应页要返回的stockcode
     * @param page
     * @param query
     * @return
     */
    public List<String> getAnsOnPage(int page, String query){
        List<String> list = new ArrayList<String>();
        list = getStockCodeList(query);
        int start = (page - 1) * pageRecord;
        int end = start + pageRecord - 1;
        List<String> res = new ArrayList<>();
        res = list.subList(start,end + 1);
        return res;
    }

    /**
     * 返回要显示的页码总数
     * @param query
     * @return
     */
    public Long getPageNum(String query){
        List<String> list = new ArrayList<String>();
        list = getStockCodeList(query);
        long num = list.size();
        long page = num / pageRecord + 1;
        return page;
    }

}
