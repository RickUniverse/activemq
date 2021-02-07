package org.activemq.spring;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author lijichen
 * @date 2021/2/7 - 21:16
 */
@Component
public class MyMessageListener implements MessageListener {
    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        System.out.println("Linstener receive message :"  + textMessage.getText());
    }
}
