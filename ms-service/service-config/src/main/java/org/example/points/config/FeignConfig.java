package org.example.points.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * <h1>Feign 调用时, 把 Header 也传递到服务提供方</h1>
 */
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor headerInterceptor() {
        return template -> {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(attributes)) {
                final HttpServletRequest request = attributes.getRequest();
                final Enumeration<String> headerNames = request.getHeaderNames();

                if (Objects.nonNull(headerNames)) {
                    while (headerNames.hasMoreElements()) {
                        final String name = headerNames.nextElement();
                        final String values = request.getHeader(name);
                        if (!name.equalsIgnoreCase("content-length")) {
                            template.header(name, values);
                        }
                    }
                }
            }
        };
    }
}
