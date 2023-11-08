package org.example.points.search.controller;

import org.example.points.common.vo.CommonResponse;
import org.example.points.search.service.SearchService;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @GetMapping("")
    public CommonResponse<List<Article>> search(String keyword) {
        final List<Article> search = searchService.search(keyword);
        return new CommonResponse<>(200, "success", search);
    }

    @PostMapping
    public CommonResponse<String> create() {
        final IndexOperations ops = elasticsearchRestTemplate.indexOps(Article.class);
        if (ops.exists()) {
            ops.delete();
            ops.create();
            ops.refresh();
            ops.putMapping(Article.class);
        }
        return new CommonResponse<>(200, "success");
    }
}
