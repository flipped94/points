package org.example.points.article.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.points.article.ArticleVO;
import org.example.points.article.entity.Article;
import org.example.points.article.mapper.ArticleMapper;
import org.example.points.article.portal.PortalArticleQueryParam;
import org.example.points.article.service.IPortalArticleService;
import org.example.points.article.service.remote.AuthorService;
import org.example.points.author.AuthorInfo;
import org.example.points.common.enums.YesOrNo;
import org.example.points.common.vo.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
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

    @Resource
    private AuthorService authorService;

    @Override
    public PageResult<ArticleVO> portal(Integer columnId, PortalArticleQueryParam queryParam) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(queryParam.getCategory()), "category", queryParam.getCategory())
                .eq("is_delete", YesOrNo.NO.type)
                .eq("column_id", columnId)
                .orderByDesc("create_time");
        final Page<Article> page = page(new Page<>(queryParam.getCurrentPage(), queryParam.getPageSize()), queryWrapper);
        return handle(page);
    }

    @Override
    public PageResult<ArticleVO> authorPublished(Integer authorId, org.example.points.common.vo.Page param) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(authorId), "author_id", authorId)
                .eq("is_delete", YesOrNo.NO.type)
                .orderByDesc("create_time");
        final Page<Article> page = page(new Page<>(param.getCurrentPage(), param.getPageSize()), queryWrapper);
        return handle(page);
    }

    private PageResult<ArticleVO> handle(Page<Article> page) {
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return new PageResult<>(page.getCurrent(), page.getSize(), page.getPages(), Collections.emptyList());
        }
        final List<Article> records = page.getRecords();
        final Integer authorId = records.get(0).getAuthorId();
        AuthorInfo authorInfo = authorService.findById(authorId);
        final List<ArticleVO> vo = Article.toVO(records);
        vo.forEach(articleVO -> articleVO.setAuthor(authorInfo));
        return new PageResult<>(page.getCurrent(), page.getSize(), page.getPages(), vo);
    }

}
