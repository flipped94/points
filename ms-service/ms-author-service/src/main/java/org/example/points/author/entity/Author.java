package org.example.points.author.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.example.points.author.AuthorInfo;
import org.example.points.common.Avatar;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 作者表
 * </p>
 *
 * @author flipped
 * @since 2023-10-18
 */
@Getter
@Setter
@TableName("t_author")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账户id
     */
    private Integer accountId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 简介
     */
    private String description;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县
     */
    private String district;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    public AuthorInfo toAuthorInfo() {
        return AuthorInfo.builder()
                ._id(accountId)
                .createAt(createdTime)
                .email(email)
                .nickName(nickName)
                .avatar(new Avatar(1, avatar, null))
                .description(description)
                .build();
    }
}
