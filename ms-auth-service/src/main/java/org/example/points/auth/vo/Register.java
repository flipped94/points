package org.example.points.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.points.common.exception.BusinessException;

import javax.validation.constraints.NotBlank;

/**
 * <h1>用户名和密码</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register {

    /**
     * 邮箱
     */
    @NotBlank(message = "请输入邮箱")
    private String email;

    /**
     * 昵称
     */
    @NotBlank(message = "请输入昵称")
    private String nickName;


    /**
     * 密码
     */
    @NotBlank(message = "请输入密码")
    private String password;

    /**
     * 重复密码
     */
    @NotBlank(message = "请确认密码")
    private String repeatPassword;

    public void check() {
        if (!password.equals(repeatPassword)) {
            throw new BusinessException(4007, "两次密码不一致");
        }
    }

}