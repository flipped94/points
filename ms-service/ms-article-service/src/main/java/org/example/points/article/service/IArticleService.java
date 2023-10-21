package org.example.points.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.points.article.ArticleCreateReqVO;
import org.example.points.article.ArticleQueryParam;
import org.example.points.article.ArticleVO;
import org.example.points.article.entity.Article;
import org.example.points.common.vo.PageResult;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author flipped
 * @since 2023-10-16
 */
public interface IArticleService extends IService<Article> {

    ArticleVO create(ArticleCreateReqVO reqVO);

    PageResult<ArticleVO> articles(ArticleQueryParam queryParam);

    void delete(String articleId);

    void update(ArticleVO articleVO);
}
