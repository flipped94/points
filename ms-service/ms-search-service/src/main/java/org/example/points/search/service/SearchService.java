package org.example.points.search.service;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.example.points.search.controller.Article;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public List<Article> search(String keyword) {
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder.should(QueryBuilders.matchPhraseQuery("title", keyword));
        queryBuilder.should(QueryBuilders.matchPhraseQuery("plain_Text", keyword));
        final String queryString = queryBuilder.toString();
        final SearchHits<Article> searchHits = elasticsearchRestTemplate.search(new StringQuery(queryString), Article.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());

    }
}
