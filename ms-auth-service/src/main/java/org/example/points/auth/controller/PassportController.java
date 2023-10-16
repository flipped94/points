package org.example.points.auth.controller;


import org.example.points.common.constant.Constant;
import org.example.points.common.vo.CommonResponse;
import org.example.points.common.vo.LoginUserInfo;
import org.example.points.common.vo.Token;
import org.example.points.auth.service.ITokenService;
import org.example.points.auth.vo.EmailAndPassword;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 登陆注册 前端控制器
 * </p>
 *
 * @author flipped
 * @since 2023-10-14
 */
@RestController
@RequestMapping("/passport")
public class PassportController {

    @Resource
    private ITokenService tokenService;

    @PostMapping("/login")
    public CommonResponse<Void> login(@RequestBody EmailAndPassword emailAndPassword, HttpServletResponse response) {
        final Token token = tokenService.generate(emailAndPassword);
        response.setHeader(Constant.HEADER_ACCESS_TOKEN, token.getAccessToken());
        response.setHeader(Constant.HEADER_REFRESH_TOKEN, token.getRefreshToken());
        return new CommonResponse<>(200, "success");
    }

    @PostMapping("/register")
    public CommonResponse<Void> register(@RequestBody EmailAndPassword emailAndPassword, HttpServletResponse response) {
        final Token token = tokenService.registerAndGenerate(emailAndPassword);
        response.setHeader(Constant.HEADER_ACCESS_TOKEN, token.getAccessToken());
        return new CommonResponse<>(200, "success");
    }

    @GetMapping("/refresh")
    public CommonResponse<Void> refresh(HttpServletRequest request, HttpServletResponse response) {
        final String refreshToken = request.getHeader(Constant.HEADER_REFRESH_TOKEN);
        final String accessToken = tokenService.refresh(refreshToken);
        final Token token = new Token(refreshToken, accessToken);
        response.setHeader(Constant.HEADER_ACCESS_TOKEN, token.getAccessToken());
        response.setHeader(Constant.HEADER_REFRESH_TOKEN, refreshToken);
        return new CommonResponse<>(200, "success");
    }

    @GetMapping("/{accessToken}")
    public CommonResponse<LoginUserInfo> parse(@PathVariable("accessToken") String accessToken) {
        final LoginUserInfo loginUserInfo = tokenService.parse(accessToken);
        return new CommonResponse<>(200, "success", loginUserInfo);
    }

}
