package org.example.minio.config;

import io.minio.MinioClient;
import org.example.minio.MinoProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration

@EnableConfigurationProperties(MinoProperties.class)
public class MinioAutoConfigure {

    @Resource
    private MinoProperties minoProperties;

    @Bean
    @ConditionalOnClass(MinioClient.class)
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minoProperties.getEndpoint())
                .credentials(minoProperties.getAccessKey(), minoProperties.getSecretKey())
                .build();
    }
}
