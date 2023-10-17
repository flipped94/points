package org.example.points.article.portal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.points.common.vo.Page;

@EqualsAndHashCode(callSuper = true)
@Data
public class PortalArticleQueryParam extends Page {
    private Integer category;
}
