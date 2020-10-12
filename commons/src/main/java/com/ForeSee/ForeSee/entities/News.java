package com.ForeSee.ForeSee.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.sql.Date;


/**
 * @Authore Legion
 * @Create 2020/10/12 0:33
 */

@Data
@Document(collection = "NewsSample")
public class News implements Serializable {
    @Field("news_title")
    private String title;
    @Field("news_time")
    private Date time;
//    private String news_time;

    @Field("news_link")
    private String link;

//    public void setTime(String news_time) {
//        this.time = Date.valueOf(news_time);
//    }
}
