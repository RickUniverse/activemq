package org.activemq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

/**
 * @author lijichen
 * @date 2021/2/7 - 20:39
 */
@Service
public class SpringMQ_Consumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Consumer springMQ_Consumer = (SpringMQ_Consumer) applicationContext.getBean("springMQ_Consumer");

        String message = (String) springMQ_Consumer.jmsTemplate.receiveAndConvert();

        System.out.println("---Consumer receive message :" + message);
    }
}
