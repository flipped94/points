package org.example.points.auth.mapper;

import org.example.points.auth.entity.AccessToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 访问token Mapper 接口
 * </p>
 *
 * @author flipped
 * @since 2023-10-14
 */
public interface AccessTokenMapper extends BaseMapper<AccessToken> {

    AccessToken findByToken(String token);

    AccessToken findByRefreshToken(String refreshToken);
}
