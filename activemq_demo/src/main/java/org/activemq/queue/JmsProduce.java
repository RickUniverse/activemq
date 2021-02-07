package org.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author lijichen
 * @date 2021/2/7 - 15:33
 */
public class JmsProduce {

    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号，如果用自己的需要改动
    public static final String ACTIVEMQ_URI = "tcp://192.168.43.154:61616";
    public static final String QUEUE_NAME = "queue01";


    public static void main(String[] args) throws JMSException {

        // 1,创建默认默认用户名密码的连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URI);

        // 2,获取连接
        Connection connection = activeMQConnectionFactory.createConnection();
        // 3，开启连接
        connection.start();
        // 4，创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5，创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        // 6，创建生产者
        MessageProducer messageProducer = session.createProducer(queue);
        // 持久化消息，默认是DeliveryMode.PERSISTENT
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // 7,发送三条消息到MQ队列中
        for (int i = 0; i < 300; i++) {
            // 8，创建消息
            TextMessage textMessage = session.createTextMessage("spend message :" + i);
            // 9, 发送给MQ
            messageProducer.send(textMessage);
        }
        // 10，关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("消息发送完成");
    }
}
