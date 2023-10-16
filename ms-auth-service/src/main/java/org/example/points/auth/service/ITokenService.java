package org.example.points.auth.service;

import org.example.points.auth.vo.EmailAndPassword;
import org.example.points.common.vo.LoginUserInfo;
import org.example.points.common.vo.Token;

public interface ITokenService {

    Token generate(EmailAndPassword emailAndPassword);

    Token registerAndGenerate(EmailAndPassword emailAndPassword);

    LoginUserInfo parse(String accessToken);

    String refresh(String refreshToken);
}
