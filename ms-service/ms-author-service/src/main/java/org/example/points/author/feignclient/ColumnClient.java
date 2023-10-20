package org.example.points.author.feignclient;

import org.example.points.column.ColumnInfo;
import org.example.points.common.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "ms-article-service")
public interface ColumnClient {

    @GetMapping("/columns/author/{authorId}")
    CommonResponse<ColumnInfo> findByAuthorId(@PathVariable("authorId") Integer authorId);
}
