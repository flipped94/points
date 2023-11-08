package org.example.points.filter;

import org.example.points.common.annotation.LoginCheck;
import org.example.points.common.constant.Constant;
import org.example.points.common.exception.BusinessException;
import org.example.points.common.vo.CommonResponse;
import org.example.points.common.vo.LoginUserInfo;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * <h1>用户身份统一登录拦截</h1>
 */
@Component
public class LoginUserInfoInterceptor implements HandlerInterceptor {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            final boolean annotationPresent = handlerMethod.getMethod().isAnnotationPresent(LoginCheck.class);
            if (!annotationPresent) {
                return true;
            }
            final String accessToken = request.getHeader(Constant.HEADER_ACCESS_TOKEN);
            final ServiceInstance instance = discoveryClient.getInstances(Constant.AUTH_SERVICE_ID).get(0);
            final String url = String.format(Constant.PARSE_TOKEN_URI, instance.getHost(), instance.getPort());
            CommonResponse<LoginUserInfo> loginUserResponse = restTemplate.exchange(url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<CommonResponse<LoginUserInfo>>() {
                    }, accessToken).getBody();
            if (Objects.isNull(loginUserResponse) || Objects.isNull(loginUserResponse.getData())) {
                throw new BusinessException(4004, "未登录");
            }
            AccessContext.setLoginUserInfo(loginUserResponse.getData());
            return true;
        } else if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (null != AccessContext.getLoginUserInfo()) {
            AccessContext.clearLoginUserInfo();
        }
    }
}
