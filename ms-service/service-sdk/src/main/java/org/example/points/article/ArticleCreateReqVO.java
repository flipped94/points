package org.example.points.article;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ArticleCreateReqVO {
    @NotBlank(message = "文章标题不能为空")
    @Length(max = 30, message = "文章标题长度不能超过30")
    private String title;

    @NotBlank(message = "文章内容不能为空")
    private String content;

    private String image;

    private Integer column;

    private Integer author;

    //    @NotNull(message = "请选择文章领域")
    private Integer categoryId;

    /*@NotNull(message = "请选择正确的文章封面类型")
    @Min(value = 1, message = "请选择正确的文章封面类型")
    @Max(value = 2, message = "请选择正确的文章封面类型")*/
    private Integer articleType;
    private String articleCover;

    /* @NotNull(message = "文章发布类型不正确")
     @Min(value = 0, message = "文章发布类型不正确")
     @Max(value = 1, message = "文章发布类型不正确")*/
    private Integer isAppoint;

    //    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss") // 前端日期字符串传到后端后，转换为Date类型
    private LocalDateTime publishTime;

    public void check() {
        // 判断文章封面类型，单图必填，纯文字则设置为空
//        if (Objects.equals(articleType, ArticleCoverType.ONE_IMAGE.type)) {
//            if (StringUtils.isBlank(articleCover)) {
//                throw new BusinessException(4100, "文章封面必填");
//            }
//        } else if (Objects.equals(articleType, ArticleCoverType.WORDS.type)) {
//            articleCover = "";
//        }
    }

}
