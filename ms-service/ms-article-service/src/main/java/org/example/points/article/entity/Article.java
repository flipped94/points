package org.example.points.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.example.points.article.ArticleCreateReqVO;
import org.example.points.article.ArticleVO;
import org.example.points.common.enums.ArticleAppointType;
import org.example.points.common.enums.ArticleReviewStatus;
import org.example.points.common.enums.YesOrNo;
import org.example.points.filter.AccessContext;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author flipped
 * @since 2023-10-16
 */
@Getter
@Setter
@TableName("t_articles")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容，长度不超过9999，需要在前后端判断
     */
    private String content;

    /**
     * 文章所属分类id
     */
    private Integer categoryId;

    /**
     * 文章类型，1：图文（1张封面），2：纯文字
     */
    private Integer articleType;

    /**
     * 文章封面图，article_type=1 的时候展示
     */
    private String articleCover;

    /**
     * 是否是预约定时发布的文章，1：预约（定时）发布，0：即时发布    在预约时间到点的时候，把1改为0，则发布
     */
    private Integer isAppoint;

    /**
     * 文章状态，1：审核中（用户已提交），2：机审结束，等待人工审核，3：审核通过（已发布），4：审核未通过；5：文章撤回（已发布的情况下才能撤回和删除）
     */
    private Integer articleStatus;

    /**
     * 发布者用户id
     */
    private Integer publishUserId;

    /**
     * 文章发布时间（也是预约发布的时间）
     */
    private LocalDateTime publishTime;

    /**
     * 用户累计点击阅读数（喜欢数）（点赞） - 放redis
     */
    private Integer readCounts;

    /**
     * 文章评论总数。评论防刷，距离上次评论需要间隔时间控制几秒
     */
    private Integer commentCounts;

    private String mongoFileId;

    /**
     * 逻辑删除状态，非物理删除，1：删除，0：未删除
     */
    private Integer isDelete;

    /**
     * 文章的创建时间
     */
    private LocalDateTime createTime;

    /**
     * 文章的修改时间
     */
    private LocalDateTime updateTime;

    public static Article toEntity(ArticleCreateReqVO reqVO) {
        Article article = new Article();
        BeanUtils.copyProperties(reqVO, article);

        article.setArticleStatus(ArticleReviewStatus.REVIEWING.type);
        article.setCommentCounts(0);
        article.setReadCounts(0);
        article.setPublishUserId(AccessContext.getLoginUserInfo().getId());

        article.setIsDelete(YesOrNo.NO.type);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        if (Objects.equals(article.getIsAppoint(), ArticleAppointType.TIMING.type)) {
            article.setPublishTime(reqVO.getPublishTime());
        } else if (Objects.equals(article.getIsAppoint(), ArticleAppointType.IMMEDIATELY.type)) {
            article.setPublishTime(LocalDateTime.now());
        }
        return article;
    }

    public static ArticleVO toVO(Article article) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        return articleVO;
    }

    public static List<ArticleVO> toVO(List<Article> articles) {
        List<ArticleVO> articleVOS = new ArrayList<>();
        for (Article article : articles) {
            articleVOS.add(toVO(article));
        }
        return articleVOS;
    }
}
