package com.ForeSee.ForeSee.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * 用于配置mongodb，手写config主要是为了用连接池
 * @Authore Legion
 * @Create 2020/10/12 1:24
 */
@Configuration
public class MongoConfig {
    @Autowired
    private MongoProperties mongoProperties;

    @Bean
    @ConfigurationProperties(prefix = "spring.data.mongodb.custom")
    MongoProperties mongoSettingProperties(){
        return new MongoProperties();
    }

    @Bean
    MongoDbFactory mongoDbFactory(){
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(mongoProperties.getConnectionsPerHost());
        builder.minConnectionsPerHost(mongoProperties.getMinConnectionsPerHost());
        MongoClientOptions mongoClientOptions = builder.build();
        ServerAddress serverAddress = new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort());
        MongoClient mongoClient = new MongoClient(serverAddress, mongoClientOptions);
        return new SimpleMongoDbFactory(mongoClient, mongoProperties.getDatabase());

    }
}
