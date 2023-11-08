package org.example.points.article.mq.consumer;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.example.points.article.service.IArticleContentService;
import org.example.points.common.utils.MarkdownUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 保存到ES
 */
@Slf4j
@Component
@RocketMQMessageListener(nameServer = "192.168.137.133:9876", topic = "article-publish", consumerGroup = "articleOssGroup")
public class ArticleContentListener implements RocketMQListener<String> {

    @Resource
    private IArticleContentService articleContentService;

    @Override
    public void onMessage(String s) {
        final ArticlePublish articlePublish = JSON.parseObject(s, ArticlePublish.class);
        final String text = MarkdownUtil.markdownToPlainText(articlePublish.getContent());
        final String excerpt = text.substring(0, 100);
        articleContentService.UpdateByArticleId(articlePublish.articleId, text, excerpt);
    }

    @Data
    public static class ArticlePublish {
        private Integer articleId;
        private String content;
    }

}

