package org.example.points.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.example.points.author.AuthorInfo;
import org.example.points.common.Avatar;

import java.time.LocalDateTime;

@Data
@Builder
public class ArticleVO {

    /**
     * id
     */
    private Integer _id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 摘录
     */
    private String excerpt;

    private Boolean isHTML;

    /**
     * 文章内容，长度不超过9999，需要在前后端判断 isHTML为true则是html链接否则是原始内容
     */
    private String content;

    /**
     * 封面图
     */
    private Avatar image;

    /**
     * 作者
     */
    private AuthorInfo author;

    private String column;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;


    // ========== 待扩展信息 =========

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
    private Integer status;

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

}
