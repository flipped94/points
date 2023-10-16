package org.example.points.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>登录用户信息</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfo {

    /**
     * 用户 id
     */
    private Integer id;

    /**
     * 用户邮箱
     */
    private String email;
}
