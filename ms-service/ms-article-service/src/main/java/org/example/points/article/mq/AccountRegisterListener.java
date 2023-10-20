package org.example.points.article.mq;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.example.points.article.service.IColumnsService;
import org.example.points.column.ColumnCreate;
import org.example.points.mq.AccountRegister;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
@RocketMQMessageListener(nameServer = "192.168.137.133:9876", topic = "account-register", consumerGroup = "columnGroup")
public class AccountRegisterListener implements RocketMQListener<String> {

    @Resource
    private IColumnsService columnsService;

    @Override
    public void onMessage(String accountRegisterStr) {
        final AccountRegister accountRegister = JSON.parseObject(accountRegisterStr, AccountRegister.class);
        int i = 0;
        try {
            for (; i < 3; i++) {
                String title = String.format("%s的专栏", accountRegister.getNickName());
                String description = String.format("这是的%s专栏，有一段非常有意思的简介，可以更新一下欧", accountRegister.getNickName());
                ColumnCreate create = new ColumnCreate(title, description, accountRegister.getAuthorId());
                Integer created = columnsService.create(create);
                if (created != null && created > 0) {
                    return;
                }
            }
        } catch (Exception e) {
            log.error("{}的专栏第{}次创建失败", accountRegister.getAuthorId(), i);
        }
        log.error("{}的专栏创建失败", accountRegister.getAuthorId());
    }
}
