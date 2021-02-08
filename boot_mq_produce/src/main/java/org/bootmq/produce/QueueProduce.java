package org.bootmq.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;
/**
 * @author lijichen
 * @date 2021/2/8 - 14:34
 */
@Component
public class QueueProduce {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void produceSendMessage() {
        jmsMessagingTemplate.convertAndSend(queue,"****"+ UUID.randomUUID().toString().substring(0,6));
    }

    @Scheduled(fixedDelay = 3000)
    public void produceSendScheduleMessage() {
        jmsMessagingTemplate.convertAndSend(queue,"****produceSendScheduleMessage:"+ UUID.randomUUID().toString().substring(0,6));
        System.out.println("----produceSendScheduleMessage");
    }
}
