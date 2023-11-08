package org.example.points.article.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.points.article.entity.ArticleContent;

import java.time.LocalDateTime;
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

    default boolean UpdateByArticleId(Integer articleId, String plainText, String excerpt) {
        UpdateWrapper<ArticleContent> wrapper = new UpdateWrapper<>();
        wrapper.eq("article_id", articleId)
                .set("plain_text", plainText)
                .set("excerpt", excerpt)
                .set("updated_time", LocalDateTime.now());
        return update(wrapper);
    }
}
