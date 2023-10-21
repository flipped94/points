package org.example.points.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.points.article.entity.ArticleContent;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 文章内容表 服务类
 * </p>
 *
 * @author flipped
 * @since 2023-10-21
 */
public interface IArticleContentService extends IService<ArticleContent> {

    List<ArticleContent> findByArticleIds(Collection<Integer> articleIds);

    String findContentByArticleId(Integer articleId);
}
