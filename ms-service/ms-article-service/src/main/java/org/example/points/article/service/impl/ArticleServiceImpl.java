package org.example.points.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.points.article.ArticleCreateReqVO;
import org.example.points.article.ArticleQueryParam;
import org.example.points.article.ArticleVO;
import org.example.points.article.entity.Article;
import org.example.points.article.mapper.ArticleMapper;
import org.example.points.article.service.IArticleService;
import org.example.points.common.enums.YesOrNo;
import org.example.points.common.exception.BusinessException;
import org.example.points.common.vo.PageResult;
import org.example.points.filter.AccessContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    @Override
    public Integer create(ArticleCreateReqVO reqVO) {
        reqVO.check();
        // TODO 分类检查
        Article article = Article.toEntity(reqVO);
        articleMapper.insert(article);
        return article.getId();
    }

    @Override
    public PageResult<ArticleVO> articles(ArticleQueryParam queryParam) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id", AccessContext.getLoginUserInfo().getId())
                .orderByDesc("create_time");
        final Page<Article> page = page(new Page<>(queryParam.getPage(), queryParam.getPageSize()), queryWrapper);
        final List<Article> records = page.getRecords();
        return new PageResult<>(page.getCurrent(), page.getTotal(), Article.toVO(records));
    }

    @Override
    public void delete(String articleId) {
        final Article article = getById(articleId);
        if (Objects.isNull(article)) {
            throw new BusinessException(5000, "文章不存在");
        }
        if (!article.getPublishUserId().equals(AccessContext.getLoginUserInfo().getId())) {
            throw new BusinessException(5001, "权限不足");
        }
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", articleId)
                .eq("publish_user_id", AccessContext.getLoginUserInfo().getId())
                .set("is_delete", YesOrNo.YES.type);
        update(updateWrapper);
    }

    @Override
    public void update(ArticleVO articleVO) {
        final Article article = getById(articleVO.getId());
        if (Objects.isNull(article)) {
            throw new BusinessException(5000, "文章不存在");
        }
        if (!article.getPublishUserId().equals(AccessContext.getLoginUserInfo().getId())) {
            throw new BusinessException(5001, "权限不足");
        }
        BeanUtils.copyProperties(articleVO, article);
        updateById(article);
    }
}
