package org.example.points.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.points.article.entity.ArticleContent;
import org.example.points.article.mapper.ArticleContentMapper;
import org.example.points.article.service.IArticleContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 文章内容表 服务实现类
 * </p>
 *
 * @author flipped
 * @since 2023-10-21
 */
@Service("articleContentService")
public class ArticleContentServiceImpl extends ServiceImpl<ArticleContentMapper, ArticleContent> implements IArticleContentService {

    @Resource
    private ArticleContentMapper articleContentMapper;

    @Override
    public List<ArticleContent> findByArticleIds(Collection<Integer> articleIds) {
        QueryWrapper<ArticleContent> wrapper = new QueryWrapper<>();
        wrapper.in("article_id", articleIds);
        return getBaseMapper().selectList(wrapper);
    }

    @Override
    public String findContentByArticleId(Integer articleId) {
        return articleContentMapper.findContentByArticleId(articleId);
    }
}
