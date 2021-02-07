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
public class SpringMQ_Producer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Producer springMQ_producer = (SpringMQ_Producer) applicationContext.getBean("springMQ_Producer");

        /**
         * 因为有配置：所以这里不用指定:spring-active-queue
         * <!-- 队列目的地 -->
         *     <bean id="destinationQueue" class="org.apache.activemq.command.ActiveMQQueue">
         *         <constructor-arg index="0" value="spring-active-queue"></constructor-arg>
         *     </bean>
         */
        springMQ_producer.jmsTemplate.send((session) -> {
            TextMessage textMessage = session.createTextMessage("This is Spring mix ActionMQ case.........");
            return textMessage;
        });

        System.out.println("---Task over---");
    }
}
