package org.example.points.search.controller;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "article")
public class Article {

    @Id
    @Field(type = FieldType.Long)
    private Integer articleId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String excerpt;

    @Field(type = FieldType.Text, name = "plain_text", analyzer = "ik_max_word")
    private String plainText;
}
