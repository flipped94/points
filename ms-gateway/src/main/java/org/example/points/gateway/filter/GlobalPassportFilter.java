package org.example.points.gateway.filter;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.example.points.common.constant.Constant;
import org.example.points.common.exception.BusinessException;
import org.example.points.common.vo.CommonResponse;
import org.example.points.common.vo.LoginUserInfo;
import org.example.points.gateway.config.WhiteList;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;


/**
 * <h1>全局登录鉴权过滤器</h1>
 */
@Component
public class GlobalPassportFilter implements GlobalFilter, Ordered {

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Resource
    private WebClient webClient;

    @Resource
    private WhiteList whiteList;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final ServerHttpResponse response = exchange.getResponse();
        final String path = request.getURI().getPath();
        for (int i = 0; i < whiteList.getUrls().size(); i++) {
            if (path.contains(whiteList.getUrls().get(i))) {
                return chain.filter(exchange);
            }
        }
        boolean run;
        try {
            run = run(request);
        } catch (BusinessException e) {
            final String s = JSON.toJSONString(new CommonResponse<String>(e.getCode(), e.getMessage()));
            final DataBuffer wrap = response.bufferFactory().wrap(s.getBytes());
            response.getHeaders().set("Content-Type", "application/json");
            return response.writeWith(Mono.just(wrap));
        }
        if (run) {
            return chain.filter(exchange);
        }
        final DataBuffer buffer = response.bufferFactory().wrap("{\"code:\" 4004,\"message\":\"未登录\",\"data\": null}".getBytes());
        response.getHeaders().set("Content-Type", "application/json");
        return response.writeWith(Mono.just(buffer));
    }

    private boolean run(ServerHttpRequest request) throws BusinessException {
        final String accessToken = request.getHeaders().getFirst(Constant.HEADER_ACCESS_TOKEN);
        if (StringUtils.isBlank(accessToken)) {
            return false;
        }
        CommonResponse<LoginUserInfo> loginUserInfoResponse = null;
        try {
            loginUserInfoResponse =
                    Mono.fromFuture(CompletableFuture.supplyAsync(() -> loadBalancerClient.choose(Constant.AUTH_SERVICE_ID)))
                            .flatMap((Function<ServiceInstance, Mono<CommonResponse<LoginUserInfo>>>) serviceInstance -> {
                                final String url = String.format(Constant.PARSE_TOKEN_URI, serviceInstance.getHost(), serviceInstance.getPort());
                                return webClient.get()
                                        .uri(url, accessToken)
                                        .retrieve()
                                        .bodyToMono(new ParameterizedTypeReference<CommonResponse<LoginUserInfo>>() {
                                        });
                            }).toFuture().get();
        } catch (InterruptedException | ExecutionException ignored) {
        }
        if (Objects.isNull(loginUserInfoResponse)) {
            return false;
        }
        if (Objects.isNull(loginUserInfoResponse.getData())) {
            throw new BusinessException(loginUserInfoResponse.getCode(), loginUserInfoResponse.getMessage());
        }
        return true;
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }
}
