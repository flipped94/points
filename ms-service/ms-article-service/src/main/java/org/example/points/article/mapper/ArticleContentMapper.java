package org.example.points.article.mapper;

import org.example.points.article.entity.ArticleContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 文章内容表 Mapper 接口
 * </p>
 *
 * @author flipped
 * @since 2023-10-21
 */
public interface ArticleContentMapper extends BaseMapper<ArticleContent> {

    String findContentByArticleId(Integer articleId);
}
