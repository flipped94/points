package org.example.points.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * <h1>用户名和密码</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailAndPassword {

    /**
     * 邮箱
     */
    @NotBlank(message = "请输入邮箱")
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "请输入密码")
    private String password;
}