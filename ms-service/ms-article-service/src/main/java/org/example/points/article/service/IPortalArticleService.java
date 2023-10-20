package org.example.points.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.points.article.portal.PortalArticleQueryParam;
import org.example.points.article.ArticleVO;
import org.example.points.article.entity.Article;
import org.example.points.common.vo.Page;
import org.example.points.common.vo.PageResult;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author flipped
 * @since 2023-10-16
 */
public interface IPortalArticleService extends IService<Article> {


    PageResult<ArticleVO> portal(Integer columnId,PortalArticleQueryParam queryParam);

    PageResult<ArticleVO> authorPublished(Integer authorId, Page param);
}
