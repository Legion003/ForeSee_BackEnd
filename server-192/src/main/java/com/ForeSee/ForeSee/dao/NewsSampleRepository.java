package com.ForeSee.ForeSee.dao;

import com.ForeSee.ForeSee.entities.Industry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


/**
 * @Authore Legion
 * @Create 2020/10/12 1:01
 */

@Component
@Slf4j
public class NewsSampleRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Industry getIndustryByCode(String code){
        Query query = new Query(Criteria.where("industryCode").is(code));
        return mongoTemplate.findOne(query, Industry.class);
    }
}
