package org.example.points.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.example.points.article.PortalArticleQueryParam;
import org.example.points.article.ArticleVO;
import org.example.points.article.entity.Article;

import java.util.List;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author flipped
 * @since 2023-10-16
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<ArticleVO> selectPageVo(IPage<ArticleVO> page, @Param("param") PortalArticleQueryParam param);

}
