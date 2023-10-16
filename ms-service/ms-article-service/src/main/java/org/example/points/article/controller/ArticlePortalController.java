package org.example.points.article.controller;

import org.example.points.article.ArticleVO;
import org.example.points.article.PortalArticleQueryParam;
import org.example.points.article.service.IPortalArticleService;
import org.example.points.common.vo.CommonResponse;
import org.example.points.common.vo.Page;
import org.example.points.common.vo.PageResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * <h1>门户端文章业务</h1>
 */
@RestController
@RequestMapping("/article/portal")
public class ArticlePortalController {

    @Resource(name = "portalArticleService")
    private IPortalArticleService portalArticleService;

    /**
     * 首页查询文章列表
     */
    @GetMapping("")
    public CommonResponse<PageResult<ArticleVO>> portal(PortalArticleQueryParam queryParam) {
        PageResult<ArticleVO> result = portalArticleService.portal(queryParam);
        return new CommonResponse<>(200, "success", result);
    }

    /**
     * 首页查询热闻列表
     */
    @GetMapping("/hotList")
    public CommonResponse<ArticleVO> hotList() {
        return null;
    }

    /**
     * 查询作家发布的所有文章列表
     */
    @GetMapping("/author/{authorId}")
    public CommonResponse<PageResult<ArticleVO>> authorPublished(@PathVariable("authorId") @NotBlank(message = "作者不能为空") Integer authorId,
                                                                 Page param) {
        PageResult<ArticleVO> result = portalArticleService.authorPublished(authorId, param);
        return new CommonResponse<>(200, "success", result);
    }

    /**
     * 阅读文章，文章阅读量累加
     */
    @PostMapping("/{articleId}")
    public CommonResponse<Void> readArticle(@PathVariable("articleId") String articleId) {
        return null;
    }
}