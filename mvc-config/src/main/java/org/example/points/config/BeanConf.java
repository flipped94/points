package org.example.points.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConf {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
