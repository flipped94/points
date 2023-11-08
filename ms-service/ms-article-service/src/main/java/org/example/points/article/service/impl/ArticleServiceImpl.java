package org.example.points.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.points.article.ArticleCreateReqVO;
import org.example.points.article.ArticleQueryParam;
import org.example.points.article.ArticleVO;
import org.example.points.article.entity.Article;
import org.example.points.article.entity.ArticleContent;
import org.example.points.article.mapper.ArticleMapper;
import org.example.points.article.mq.producer.ArticlePublishProducer;
import org.example.points.article.service.IArticleContentService;
import org.example.points.article.service.IArticleService;
import org.example.points.article.service.remote.AuthorService;
import org.example.points.author.AuthorInfo;
import org.example.points.common.enums.YesOrNo;
import org.example.points.common.exception.BusinessException;
import org.example.points.common.vo.PageResult;
import org.example.points.filter.AccessContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
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
@Service(value = "articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticlePublishProducer articlePublishProducer;

    @Resource
    private IArticleContentService articleContentService;

    @Resource
    private AuthorService authorService;

    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public ArticleVO create(ArticleCreateReqVO reqVO) {
        reqVO.check();
        Article article = Article.toEntity(reqVO);
        ArticleContent articleContent = new ArticleContent();
        articleContent.setTitle(reqVO.getTitle());
        articleContent.setContent(reqVO.getContent());
        articleContent.setCreatedTime(article.getCreatedTime());
        transactionTemplate.execute(status -> {
            save(article);
            articleContent.setArticleId(article.getId());
            articleContentService.save(articleContent);
            return null;
        });
        articlePublishProducer.publish(article.getId(), reqVO.getContent());
        final ArticleVO articleVO = handleAuthor(article);
        articleVO.setContent(reqVO.getContent());
        return articleVO;
    }

    @Override
    public PageResult<ArticleVO> articles(ArticleQueryParam queryParam) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id", AccessContext.getLoginUserInfo().getId())
                .eq("is_delete", YesOrNo.NO.type)
                .eq(Objects.nonNull(queryParam.getStatus()), "status", queryParam.getStatus())
                .orderByDesc("create_time");
        final Page<Article> page = page(new Page<>(queryParam.getCurrentPage(), queryParam.getPageSize()), queryWrapper);
        final List<Article> records = page.getRecords();
        return new PageResult<>(page.getCurrent(), page.getSize(), page.getPages(), Article.toVO(records));
    }

    @Override
    public void delete(String articleId) {
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", articleId)
                .eq("author_id", AccessContext.getLoginUserInfo().getId())
                .set("is_delete", YesOrNo.YES.type);
        final boolean deleted = update(updateWrapper);
        if (!deleted) {
            throw new BusinessException(5001, "删除失败");
        }
    }

    @Override
    public void update(ArticleVO articleVO) {
        final Article article = new Article();
        BeanUtils.copyProperties(articleVO, article);
        article.setAuthorId(AccessContext.getLoginUserInfo().getId());
        final boolean updated = updateById(article);
        if (!updated) {
            throw new BusinessException(5001, "修改失败");
        }
    }

    private ArticleVO handleAuthor(Article article) {
        final Integer authorId = article.getAuthorId();
        final AuthorInfo authorInfo = authorService.findById(authorId);
        final ArticleVO vo = Article.toVO(article);
        vo.setAuthor(authorInfo);
        return vo;
    }
}
