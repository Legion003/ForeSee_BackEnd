package com.ForeSee.ForeSee.dao;

import com.ForeSee.ForeSee.entities.Industry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Authore Legion
 * @Create 2020/10/12 1:08
 */
@SpringBootTest()
@Slf4j
public class NewsSampleRepositoryTest {

    @Autowired
    private NewsSampleRepository newsSampleRepository;

    @Test
    public void getIndustryByCode(){
        Industry industry = newsSampleRepository.getIndustryByCode("BK0537");
        System.out.println(industry);
    }
}
