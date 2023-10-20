package org.example.points.article.service.remote;

import org.example.points.article.feignclient.AuthorClient;
import org.example.points.author.AuthorInfo;
import org.example.points.common.vo.CommonResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthorService {

    @Resource
    private AuthorClient authorClient;

    public AuthorInfo findById(Integer authorId) {
        final CommonResponse<AuthorInfo> response = authorClient.findAuthorById(authorId);
        return response.getData();
    }
}
