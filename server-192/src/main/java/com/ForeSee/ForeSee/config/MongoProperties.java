package com.ForeSee.ForeSee.config;

import lombok.Data;


/**
 * 用于读取MongoDb配置
 * @Authore Legion
 * @Create 2020/10/12 1:24
 */

@Data
public class MongoProperties {
    private String host;
    private Integer port;
    private String database;
    private Integer connectionsPerHost = 100;
    private Integer minConnectionsPerHost;

}
