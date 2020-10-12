package com.ForeSee.ForeSee.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Authore Legion
 * @Create 2020/10/12 0:33
 */

@Data
@Document(collection = "NewsSample")
public class Company implements Serializable {
    @Field("stock_code")
    private String companyCode;
    @Field("stock_name")
    private String name;
    @Field("news")
    private List<News> news;
}
