package org.example.points.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorCreate {
    /**
     * 账号id
     */
    @NotNull(message = "账号不能为空")
    private Integer accountId;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickName;
}
