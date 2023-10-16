package org.example.points.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.points.auth.entity.RefreshToken;
import org.example.points.auth.mapper.RefreshTokenMapper;
import org.example.points.auth.service.IRefreshTokenService;
import org.example.points.auth.utils.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author flipped
 * @since 2023-10-14
 */
@Service
public class RefreshTokenServiceImpl extends ServiceImpl<RefreshTokenMapper, RefreshToken> implements IRefreshTokenService {

    @Resource
    private RefreshTokenMapper refreshTokenMapper;

    @Override
    public String create(Integer userId) {
        RefreshToken token = new RefreshToken();
        final String tokenStr = TokenUtil.generate();
        token.setUserId(userId);
        token.setToken(tokenStr);
        token.setCreatedTime(LocalDateTime.now());
        token.setExpirationTime(LocalDateTime.now().plusDays(30));
        refreshTokenMapper.insert(token);
        return tokenStr;
    }

    @Override
    public RefreshToken findByToken(String refreshTokenStr) {
        return refreshTokenMapper.findByToken(refreshTokenStr);
    }
}
