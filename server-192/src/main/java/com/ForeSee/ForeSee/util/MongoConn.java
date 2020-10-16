package com.ForeSee.ForeSee.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MongoConn {

    public static MongoDatabase getConn(){
        MongoDatabase mongoDatabase = null;
        MongoClient mongoClient = null;
        try{
            mongoClient = new MongoClient("localhost",27017);
            mongoDatabase = mongoClient.getDatabase("mydb");
        }catch (Exception e){
            log.error(e.getClass().getName()+": "+e.getMessage());
        }
        return mongoDatabase;

    }

}
