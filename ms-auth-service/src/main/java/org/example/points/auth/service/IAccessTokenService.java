package org.example.points.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.points.auth.entity.AccessToken;

/**
 * <p>
 * 访问token 服务类
 * </p>
 *
 * @author flipped
 * @since 2023-10-14
 */
public interface IAccessTokenService extends IService<AccessToken> {

    String create(String refreshToken, Integer userId);

    AccessToken findByToken(String token);

    AccessToken findByRefreshToken(String refreshToken);
}
