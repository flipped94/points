package org.example.points.article.feignclient;

import org.example.points.author.AuthorInfo;
import org.example.points.common.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "ms-author-service")
public interface AuthorClient {

    @GetMapping("/author/{id}")
    CommonResponse<AuthorInfo> findAuthorById(@PathVariable("id") Integer id);
}
