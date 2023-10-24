package org.example.points.search.controller;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Document(indexName = "stu")
public class Article {

    @Id
    private Integer id;

    @Field(analyzer = "ik_max_work")
    private String title;

    @Field(analyzer = "ik_max_work")
    private String excerpt;

    @Field(analyzer = "ik_max_work")
    private String content;
}
