package org.example.points.article.mq;

import com.alibaba.fastjson.JSON;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.example.points.article.entity.Article;
import org.example.points.article.service.IArticleService;
import org.example.points.article.service.IHtmlService;
import org.example.points.common.enums.YesOrNo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Component
@RocketMQMessageListener(nameServer = "192.168.137.133:9876", topic = "article-publish", consumerGroup = "articleGroup")
public class ArticlePublishedListener implements RocketMQListener<String> {

    @Resource
    private IArticleService articleService;

    @Resource
    private IHtmlService IHtmlService;

    @Resource
    private MinioClient minioClient;


    @Override
    public void onMessage(String s) {
        final ArticlePublish articlePublish = JSON.parseObject(s, ArticlePublish.class);
        final String html = IHtmlService.markdownToHtml(articlePublish.getContent());
        final Article article = articleService.getById(articlePublish.articleId);
        InputStream inputStream = IOUtils.toInputStream(html);
        int i;
        for (i = 0; i < 3; i++) {
            try {
                final PutObjectArgs objectArgs = PutObjectArgs.builder()
                        .stream(inputStream, inputStream.available(), -1)
                        .object(UUID.randomUUID().toString().replace("-", "") + ".html")
                        .contentType("text/html")
                        .bucket("points")
                        .build();
                final ObjectWriteResponse response = minioClient.putObject(objectArgs);
                article.setIsHtml(YesOrNo.YES.type);
                article.setHtmlUrl("http://192.168.137.133:9000/" + response.bucket() + "/" + response.object());
                final boolean b = articleService.updateById(article);
                if (b) {
                    return;
                }
            } catch (Exception e) {
                log.error("{}", e.toString());
                e.printStackTrace();
            }
            log.error("文章编号{}第{}次静态化失败", article.getId(), i + 1);
        }
        log.error("文章编号{}静态化失败", article.getId());
    }

    @Data
    public static class ArticlePublish {
        private String articleId;
        private String content;
    }

}

