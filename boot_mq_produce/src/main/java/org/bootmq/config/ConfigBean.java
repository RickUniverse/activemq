package org.bootmq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

/**
 * @author lijichen
 * @date 2021/2/8 - 14:27
 */
@Component
@EnableJms// 开启jms适配注解
public class ConfigBean {

    @Value("${myqueue}")
    private String queueName;

    @Bean
    public ActiveMQQueue queue() {
        return new ActiveMQQueue(queueName);
    }
}
