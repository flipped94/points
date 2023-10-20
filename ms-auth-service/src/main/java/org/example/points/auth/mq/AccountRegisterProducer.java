package org.example.points.auth.mq;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AccountRegisterProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void accountRegister(Integer accountId, String email, String nickName) {
        // 发送消息
        JSONObject msg = new JSONObject();
        msg.put("authorId", accountId);
        msg.put("nickName", nickName);
        msg.put("email", email);
        rocketMQTemplate.convertAndSend("account-register", msg.toJSONString());
    }
}
