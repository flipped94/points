package org.example.points.article.controller;

import org.example.points.article.ArticleCreateReqVO;
import org.example.points.article.ArticleQueryParam;
import org.example.points.article.ArticleVO;
import org.example.points.article.service.IArticleContentService;
import org.example.points.article.service.IArticleService;
import org.example.points.common.annotation.LoginCheck;
import org.example.points.common.vo.CommonResponse;
import org.example.points.common.vo.PageResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author flipped
 * @since 2023-10-16
 */
@RestController
@RequestMapping("/article")
public class ArticlesController {

    @Resource(name = "articleService")
    private IArticleService articleService;

    @Resource
    private IArticleContentService articleContentService;

    /**
     * 发布文章
     */
    @PostMapping("")
    @LoginCheck
    public CommonResponse<ArticleVO> publish(@RequestBody @Valid ArticleCreateReqVO reqVO) {
        ArticleVO articleVO = articleService.create(reqVO);
        return new CommonResponse<>(200, "success", articleVO);
    }

    /**
     * 当前用户的文章
     */
    @GetMapping("")
    @LoginCheck
    public CommonResponse<PageResult<ArticleVO>> articles(ArticleQueryParam queryParam) {
        PageResult<ArticleVO> result = articleService.articles(queryParam);
        return new CommonResponse<>(200, "success", result);
    }

    @GetMapping("/content/{articleId}")
    public CommonResponse<String> getContent(@PathVariable("articleId") Integer articleId) {
        String content = articleContentService.findContentByArticleId(articleId);
        return new CommonResponse<>(200, "success", content);
    }

    /**
     * 用户删除文章
     */
    @DeleteMapping("/{articleId}")
    @LoginCheck
    public CommonResponse<Void> delete(@PathVariable("articleId") String articleId) {
        articleService.delete(articleId);
        return new CommonResponse<>(200, "success");
    }

    /**
     * 更新文章
     */
    @PutMapping("")
    @LoginCheck
    public CommonResponse<Void> update(@RequestBody ArticleVO articleVO) {
        articleService.update(articleVO);
        return new CommonResponse<>(200, "success");
    }

}
