package org.example.points.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.points.article.ArticleVO;
import org.example.points.article.PortalArticleQueryParam;
import org.example.points.article.entity.Article;
import org.example.points.article.mapper.ArticleMapper;
import org.example.points.article.service.IPortalArticleService;
import org.example.points.common.enums.YesOrNo;
import org.example.points.common.vo.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author flipped
 * @since 2023-10-16
 */
@Service("portalArticleService")
public class PortalArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IPortalArticleService {


    @Override
    public PageResult<ArticleVO> portal(PortalArticleQueryParam queryParam) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(queryParam.getCategory()), "category", queryParam.getCategory())
                .eq("is_delete", YesOrNo.NO.type)
                .orderByDesc("create_time");
        final Page<Article> page = page(new Page<>(queryParam.getPage(), queryParam.getPageSize()), queryWrapper);
        final List<Article> records = page.getRecords();
        return new PageResult<>(page.getCurrent(), page.getTotal(), Article.toVO(records));
    }

    @Override
    public PageResult<ArticleVO> authorPublished(Integer authorId, org.example.points.common.vo.Page param) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(authorId), "author_id", authorId)
                .eq("is_delete", YesOrNo.NO.type)
                .orderByDesc("create_time");
        final Page<Article> page = page(new Page<>(param.getPage(), param.getPageSize()), queryWrapper);
        final List<Article> records = page.getRecords();
        return new PageResult<>(page.getCurrent(), page.getTotal(), Article.toVO(records));
    }
}
