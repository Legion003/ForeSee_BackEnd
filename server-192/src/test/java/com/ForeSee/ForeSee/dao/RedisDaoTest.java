package com.ForeSee.ForeSee.dao.dao;

import com.ForeSee.ForeSee.dao.RedisDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Zenglr
 * @ClassName
 * @Description
 * @create 2020-10-16-7:16 下午
 */
@SpringBootTest
@Slf4j
public class RedisDaoTest {
    @Autowired
    RedisDao redisDao;

    @Test
    public void getStockCode(){
        String query = "大东海A";
        log.info("使用"+query+"查询");
        String result = redisDao.getStockcode(query);
        String testAns = "000613";
        log.info(result);
        Assert.assertEquals("Wrong output！",testAns,result);
    }
}
