package org.example.points.article.mq;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ArticlePublishProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void publish(Integer articleId, String content) {
        // 发送消息
        JSONObject msg = new JSONObject();
        msg.put("articleId", articleId);
        msg.put("content", content);
        rocketMQTemplate.convertAndSend("article-publish", msg.toJSONString());
    }
}