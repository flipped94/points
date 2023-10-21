package org.example.points.article.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.points.article.ArticleVO;
import org.example.points.article.entity.Article;
import org.example.points.article.entity.ArticleContent;
import org.example.points.article.mapper.ArticleMapper;
import org.example.points.article.portal.PortalArticleQueryParam;
import org.example.points.article.service.IArticleContentService;
import org.example.points.article.service.IPortalArticleService;
import org.example.points.article.service.remote.AuthorService;
import org.example.points.author.AuthorInfo;
import org.example.points.common.enums.YesOrNo;
import org.example.points.common.vo.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    private IArticleContentService articleContentService;

    @Override
    public PageResult<ArticleVO> portal(Integer columnId, PortalArticleQueryParam queryParam) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(queryParam.getCategory()), "category", queryParam.getCategory())
                .eq("is_delete", YesOrNo.NO.type)
                .eq("column_id", columnId)
                .orderByDesc("create_time");
        final Page<Article> page = page(new Page<>(queryParam.getCurrentPage(), queryParam.getPageSize()), queryWrapper);
        final List<Article> records = page.getRecords();
        final List<ArticleVO> articleVOS = handleAuthor(records);
        handleContent(articleVOS);
        return new PageResult<>(page.getCurrent(), page.getSize(), page.getPages(), articleVOS);
    }

    @Override
    public PageResult<ArticleVO> authorPublished(Integer authorId, org.example.points.common.vo.Page param) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(authorId), "author_id", authorId)
                .eq("is_delete", YesOrNo.NO.type)
                .orderByDesc("create_time");
        final Page<Article> page = page(new Page<>(param.getCurrentPage(), param.getPageSize()), queryWrapper);
        final List<Article> records = page.getRecords();
        final List<ArticleVO> articleVOS = handleAuthor(records);
        handleContent(articleVOS);
        return new PageResult<>(page.getCurrent(), page.getSize(), page.getPages(), articleVOS);
    }

    private List<ArticleVO> handleAuthor(List<Article> records) {
        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }
        final Integer authorId = records.get(0).getAuthorId();
        AuthorInfo authorInfo = authorService.findById(authorId);
        final List<ArticleVO> vo = Article.toVO(records);
        vo.forEach(articleVO -> articleVO.setAuthor(authorInfo));
        return vo;
    }

    private void handleContent(List<ArticleVO> articleVOS) {
        if (CollectionUtils.isEmpty(articleVOS)) {
            return;
        }
        final List<ArticleVO> notHtml = articleVOS.stream()
                .filter(articleVO -> !articleVO.getIsHTML())
                .collect(Collectors.toList());
        final Set<Integer> notHtmlIds = notHtml.stream().map(ArticleVO::get_id)
                .collect(Collectors.toSet());

        final Map<Integer, ArticleContent> id2Content = articleContentService.findByArticleIds(notHtmlIds)
                .stream()
                .collect(Collectors.toMap(ArticleContent::getArticleId, articleContent -> articleContent, (u1, u2) -> u2));
        notHtml.forEach(articleVO -> {
            if (id2Content.containsKey(articleVO.get_id())) {
                articleVO.setContent(id2Content.get(articleVO.get_id()).getContent());
            }
        });
    }
}
