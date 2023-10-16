package org.example.points.auth.mapper;

import org.example.points.auth.entity.RefreshToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author flipped
 * @since 2023-10-14
 */
public interface RefreshTokenMapper extends BaseMapper<RefreshToken> {

    RefreshToken findByToken(String token);
}
