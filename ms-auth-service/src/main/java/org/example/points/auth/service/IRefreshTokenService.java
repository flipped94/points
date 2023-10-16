package org.example.points.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.points.auth.entity.RefreshToken;

/**
 * <p>
 * 刷新token 服务类
 * </p>
 *
 * @author flipped
 * @since 2023-10-14
 */
public interface IRefreshTokenService extends IService<RefreshToken> {

    String create(Integer userId);

    RefreshToken findByToken(String refreshTokenStr);
}
