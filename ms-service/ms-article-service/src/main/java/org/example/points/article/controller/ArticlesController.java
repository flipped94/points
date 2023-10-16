package org.example.points.article.controller;

import org.example.points.article.ArticleCreateReqVO;
import org.example.points.article.ArticleQueryParam;
import org.example.points.article.ArticleVO;
import org.example.points.article.service.IArticleService;
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

    /**
     * 发布文章
     */
    @PostMapping("")
    public CommonResponse<Integer> publish(@RequestBody @Valid ArticleCreateReqVO reqVO) {
        Integer id = articleService.create(reqVO);
        return new CommonResponse<>(200, "success", id);
    }

    /**
     * 当前用户的文章
     */
    @GetMapping("")
    public CommonResponse<PageResult<ArticleVO>> articles(ArticleQueryParam queryParam) {
        PageResult<ArticleVO> result = articleService.articles(queryParam);
        return new CommonResponse<>(200, "success", result);
    }

    /**
     * 用户删除文章
     */
    @DeleteMapping("/{articleId}")
    public CommonResponse<Void> delete(@PathVariable("articleId") String articleId) {
        articleService.delete(articleId);
        return new CommonResponse<>(200, "success");
    }

    /**
     * 更新文章
     */
    @PutMapping("")
    public CommonResponse<Void> update(@RequestBody ArticleVO articleVO) {
        articleService.update(articleVO);
        return new CommonResponse<>(200, "success");
    }

}
