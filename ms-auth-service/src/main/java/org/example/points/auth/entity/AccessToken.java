package org.example.points.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 访问token
 * </p>
 *
 * @author flipped
 * @since 2023-10-14
 */
@Getter
@Setter
@TableName("t_access_token")
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 刷新token
     */
    private String refreshToken;

    /**
     * 访问token
     */
    private String token;

    /**
     * 过期时间
     */
    private LocalDateTime expirationTime;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
}
