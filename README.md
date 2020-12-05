# ForeSee_BackEnd

## 项目介绍

### 组织结构

```lua
ForeSee
├── client-121 -- 接收从前端发送过来的请求
├── eureka-222 -- 服务注册中心
├── server-192 -- 微服务提供者
├── gateway-222 --路由转发
```

- [client-121文档](./client-121/README.md)

- [eureka-222文档](./eureka-222/README.md)

- [gateway-222文档](./gateway-222/README.md)

- [server-192文档](./server-192/README.md)



### 技术选型

| 技术                 | 说明                | 官网                                                 |
| -------------------- | ------------------- | ---------------------------------------------------- |
| SpringBoot           | 容器+MVC框架       | https://spring.io/projects/spring-boot            |
| SpringCloud          | 微服务架构开发工具  | https://spring.io/projects/spring-cloud      |
| junit                | 单元测试            | https://junit.org/junit4           |
| log4j                | 日志记录            | http://logging.apache.org/log4j/1.2/       |
| lombok               | 简化对象封装工具    | https://github.com/rzwitserloot/lombok            |
| Redis                | 分布式缓存          | https://redis.io/                                    |
| MongoDB              | NoSql数据库         | https://www.mongodb.com                              |                                  |



## 环境搭建

### 开发工具

| 工具          | 说明                | 官网                                            |
| ------------- | ------------------- | ----------------------------------------------- |
| IDEA          | 开发IDE             | https://www.jetbrains.com/idea/download         |
| redis desktop manager | redis客户端连接工具 | https://rdm.dev/      |
| X-shell       | Linux远程连接工具   | http://www.netsarang.com/download/software.html |

### 开发环境

| 工具          | 版本号 | 下载                                                         |
| ------------- | ------ | ------------------------------------------------------------ |
| JDK           | 1.8    | https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
| Redis         | 5.0.8   | https://redis.io/download                                    |
| MongoDB | 2.6.10 | https://www.mongodb.com/ |

## 项目启动

### 准备工作

在本地使用IDEA进行打包，选择右侧工具栏的Maven Projects，点击根目录ForeSee的Lifecycle，进行clean和install，打包成功。

### 启动顺序

启动脚本有start121.sh、start222.sh和start192.sh三个。

start121.sh负责启动触发端，start222.sh负责启动服务注册中心和路由网关，start192.sh负责启动微服务端。

1.在222.200.184.74使用sh start222.sh start命令启动start222.sh。

2.在192.168.1.103使用sh start192.sh start命令启动start192.sh。

3.在121.46.19.26使用sh start121.sh start命令启动start121.sh。

### 关闭服务

使用sh start192/start121/start222.sh stop命令关闭对应主机上的服务。

### 重启服务

使用sh start192/start121/start222.sh restart命令重启对应主机上的服务。