package com.ForeSee.ForeSee.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @Authore Legion
 * @Create 2020/10/12 0:40
 */

@Data
@Document(collection = "NewsSample")
public class Industry implements Serializable {
    @Field("industryCode")
    private String IndustryCode;
    @Field("allInfo")
    private List<Company> companies;
}
