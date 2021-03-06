package com.ForeSee.ForeSee.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoConn {
    /**
     * @return Mongodb的连接
     */
    public static MongoClient getConn(){
        MongoClient mongoClient = null;
        try{
            mongoClient = new MongoClient("192.168.1.103", 40000);
        }catch (Exception e){
            log.error(e.getClass().getName()+": "+e.getMessage());
        }
        return mongoClient;

    }

}
