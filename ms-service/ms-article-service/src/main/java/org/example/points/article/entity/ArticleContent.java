package org.example.points.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章内容表
 * </p>
 *
 * @author flipped
 * @since 2023-10-21
 */
@Getter
@Setter
@TableName("t_article_content")
public class ArticleContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 文章内容，长度不超过9999，需要在前后端判断
     */
    private String content;

    /**
     * 摘录
     */
    private String excerpt;

    /**
     * 纯文本
     */
    private String plainText;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章的创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 文章的修改时间
     */
    private LocalDateTime updatedTime;
}
