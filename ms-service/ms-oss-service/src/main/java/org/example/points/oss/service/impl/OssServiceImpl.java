package org.example.points.oss.service.impl;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.example.points.common.vo.OssVO;
import org.example.points.oss.service.OssService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Resource
    private MinioClient minioClient;

    @Override
    public OssVO upload(MultipartFile file) {
        final String originalFilename = file.getOriginalFilename();
        final PutObjectArgs.Builder builder = PutObjectArgs.builder().bucket("points");
        String extension = "";
        if (Objects.nonNull(originalFilename)) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            builder.contentType(extension);
            String newName = UUID.randomUUID().toString().replace("-", "") + extension;
            builder.object(newName);
        } else {
            builder.object(UUID.randomUUID().toString().replace("-", ""));
        }
        try {
            builder.stream(file.getInputStream(), file.getInputStream().available(), -1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            final ObjectWriteResponse response = minioClient.putObject(builder.build());
            String url = "http://192.168.137.133:9000/points/" + response.object();
            return new OssVO(url, originalFilename, extension, LocalDateTime.now());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
