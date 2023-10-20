package org.example.points.author.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.example.points.author.AuthorCreate;
import org.example.points.author.service.IAuthorService;
import org.example.points.mq.AccountRegister;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Component
@RocketMQMessageListener(topic = "account-register", consumerGroup = "authorGroup")
public class AccountRegisterListener implements RocketMQListener<String> {
    @Resource
    private IAuthorService authorService;

    @Override
    public void onMessage(String accountRegisterStr) {
        final AccountRegister accountRegister = JSON.parseObject(accountRegisterStr, AccountRegister.class);
        int i = 0;
        try {
            for (; i < 3; i++) {
                AuthorCreate create = new AuthorCreate(accountRegister.getAuthorId(), accountRegister.getEmail(), accountRegister.getNickName());
                final Integer created = authorService.create(create);
                if (Objects.nonNull(created) & created > 0) {
                    return;
                }
            }
        } catch (Exception e) {
            log.error("作家{}第{}次创建失败", accountRegister.getAuthorId(), i);
        }
        log.error("作家{}创建失败", accountRegister.getAuthorId());
    }
}
