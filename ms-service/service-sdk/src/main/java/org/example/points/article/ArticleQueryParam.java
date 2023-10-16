package org.example.points.article;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.points.common.vo.Page;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleQueryParam extends Page {
    private Integer status;
}
