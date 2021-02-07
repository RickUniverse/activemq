package org.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author lijichen
 * @date 2021/2/7 - 15:33
 */
public class JmsProduce {

    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号，如果用自己的需要改动
    public static final String ACTIVEMQ_URI = "tcp://192.168.43.154:61616";
    public static final String TOPIC_NAME = "topic-news";


    public static void main(String[] args) throws JMSException {

        // 1,创建默认默认用户名密码的连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URI);

        // 2,获取连接
        Connection connection = activeMQConnectionFactory.createConnection();
        // 4，创建会话
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 开启事务的会话
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 5，创建目的地
        Topic topic = session.createTopic(TOPIC_NAME);
        // 6，创建生产者
        MessageProducer messageProducer = session.createProducer(topic);
        // 持久化topic
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // 3，开启连接
        connection.start();

        // 7,发送三条消息到MQ队列中
        for (int i = 0; i < 3; i++) {
            // 8，创建消息
            TextMessage textMessage = session.createTextMessage("spend message :" + i);
            // 9, 发送给MQ
            messageProducer.send(textMessage);

            // MapMessage
            /*MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("k1","v1");

            // 消息属性
            mapMessage.setStringProperty("property01","property01value");

            messageProducer.send(mapMessage);*/
        }
        // 10，关闭资源
        messageProducer.close();
        // 如果开启了事务，则需要commit之后才会添加到队列queue
        session.commit();
        session.close();
        connection.close();

        System.out.println("消息发送完成");
    }
}
