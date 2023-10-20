package org.example.points.author.mapper;

import org.example.points.author.entity.Author;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 作者表 Mapper 接口
 * </p>
 *
 * @author flipped
 * @since 2023-10-18
 */
public interface AuthorMapper extends BaseMapper<Author> {

    Author findByAccountId(Integer accountId);
}
