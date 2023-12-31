package org.example.points.auth.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.points.auth.entity.AccessToken;
import org.example.points.auth.entity.Account;
import org.example.points.auth.entity.RefreshToken;
import org.example.points.auth.mq.AccountRegisterProducer;
import org.example.points.auth.service.IAccessTokenService;
import org.example.points.auth.service.IAccountService;
import org.example.points.auth.service.IRefreshTokenService;
import org.example.points.auth.service.ITokenService;
import org.example.points.auth.utils.TokenUtil;
import org.example.points.auth.vo.EmailAndPassword;
import org.example.points.auth.vo.Register;
import org.example.points.common.exception.BusinessException;
import org.example.points.common.vo.LoginUserInfo;
import org.example.points.common.vo.Token;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class TokenServiceImpl implements ITokenService {

    @Resource
    private IAccountService accountService;

    @Resource
    private IRefreshTokenService refreshTokenService;

    @Resource
    private IAccessTokenService accessTokenService;

    @Resource
    private AccountRegisterProducer accountRegisterProducer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Token generate(EmailAndPassword emailAndPassword) {
        Account account = accountService.findByEmail(emailAndPassword.getEmail());
        if (Objects.isNull(account)) {
            throw new BusinessException(4001, "账号不存在");
        }

        if (!Objects.equals(account.getPassword(), emailAndPassword.getPassword())) {
            throw new BusinessException(4002, "密码错误");
        }

        final String refreshToken = refreshTokenService.create(account.getId());
        final String accessToken = accessTokenService.create(refreshToken, account.getId());
        return new Token(refreshToken, accessToken);
    }

    @Override
    @Transactional
    public Token registerAndGenerate(Register register) {
        register.check();
        final Account oldAccount = accountService.findByEmail(register.getEmail());
        if (Objects.nonNull(oldAccount)) {
            throw new BusinessException(4000, "该邮箱已注册");
        }
        final Account account = new Account();
        account.setEmail(register.getEmail());
        account.setPassword(register.getPassword());
        account.setCreatedTime(LocalDateTime.now());
        accountService.save(account);
        accountRegisterProducer.accountRegister(account.getId(), account.getEmail(), register.getNickName());
        final String refreshToken = refreshTokenService.create(account.getId());
        final String accessToken = accessTokenService.create(refreshToken, account.getId());
        return new Token(refreshToken, accessToken);
    }

    @Override
    public LoginUserInfo parse(String accessToken) {
        AccessToken token = accessTokenService.findByToken(accessToken);
        if (Objects.isNull(token)) {
            throw new BusinessException(4004, "未登录");
        }
        if (token.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(4005, "访问token已过期");
        }
        final Account account = accountService.getById(token.getUserId());
        return new LoginUserInfo(account.getId(), account.getEmail());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refresh(String refreshTokenStr) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenStr);
        if (Objects.isNull(refreshToken)) {
            throw new BusinessException(4004, "未登录");
        }
        if (refreshToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(4005, "登录已过期");
        }
        final AccessToken accessToken = accessTokenService.findByRefreshToken(refreshTokenStr);
        accessToken.setToken(TokenUtil.generate());
        final long hours = Duration.between(refreshToken.getExpirationTime(), accessToken.getExpirationTime()).toHours();
        if (hours > 2) {
            accessToken.setExpirationTime(LocalDateTime.now().plusHours(2));
        }
        final long days = Duration.between(refreshToken.getExpirationTime(), LocalDateTime.now()).toDays();
        if (days < 7) {
            refreshToken.setExpirationTime(refreshToken.getExpirationTime().plusDays(15));
            refreshTokenService.updateById(refreshToken);
        }
        accessTokenService.updateById(accessToken);
        return accessToken.getToken();
    }
}
