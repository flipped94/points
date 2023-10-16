package org.example.points.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.points.auth.entity.AccessToken;
import org.example.points.auth.mapper.AccessTokenMapper;
import org.example.points.auth.service.IAccessTokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 访问token 服务实现类
 * </p>
 *
 * @author flipped
 * @since 2023-10-14
 */
@Service
public class AccessTokenServiceImpl extends ServiceImpl<AccessTokenMapper, AccessToken> implements IAccessTokenService {

    @Resource
    private AccessTokenMapper accessTokenMapper;

    @Override
    public String create(String refreshToken, Integer userId) {
        AccessToken token = new AccessToken();
        final String tokenStr = UUID.randomUUID().toString().replace("-", "");
        token.setUserId(userId);
        token.setToken(tokenStr);
        token.setRefreshToken(refreshToken);
        final LocalDateTime now = LocalDateTime.now();
        token.setCreatedTime(now);
        token.setExpirationTime(now.plusHours(2));
        accessTokenMapper.insert(token);
        return tokenStr;
    }

    @Override
    public AccessToken findByToken(String token) {
        return accessTokenMapper.findByToken(token);
    }

    @Override
    public AccessToken findByRefreshToken(String refreshToken) {
        return accessTokenMapper.findByRefreshToken(refreshToken);
    }
}
