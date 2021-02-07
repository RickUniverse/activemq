package org.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 如果是两个消费者则使用负载均衡策略
 *
 * topic 订阅者持久化时必须先启动一次，进行注册，这样离线也可以收到消息
 * @author lijichen
 * @date 2021/2/7 - 15:57
 */
public class JmsConsumer {

    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号，如果用自己的需要改动
    public static final String ACTIVEMQ_URI = "tcp://192.168.43.154:61616";
    // EmbedBroker
    public static final String ACTIVEMQ_URI_EMBED_BROKER = "tcp://localhost:61616";

    public static final String TOPIC_NAME = "topic-news";

    public static void main(String[] args) throws JMSException, IOException {


        // 1,创建默认默认用户名密码的连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URI);

        // 2,获取连接
        Connection connection = activeMQConnectionFactory.createConnection();
        // 设置消费者的客户端id
        connection.setClientID("consumer-1");
        // 3，开启连接
        connection.start();
        // 4，创建会话，第一个参数是事务，第二个是签收
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 手动签收 ：CLIENT_ACKNOWLEDGE
        // 自动签收：AUTO_ACKNOWLEDGE
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        // 5，创建目的地
        Topic topic = session.createTopic(TOPIC_NAME);
        // 创建订阅者
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic,"remark...");
        connection.start();

        Message message = topicSubscriber.receive();
        while (null != message) {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("收到订阅过的的持久化消息：" + textMessage.getText());
                    // 如果开启手动签收需要
                    textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;
                try {
                    System.out.println("收到订阅过的的持久化消息：" + mapMessage.getString("k1") + "----" + mapMessage.getStringProperty("property01"));
                    // 如果开启手动签收需要
                    mapMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

            message = topicSubscriber.receive(5000L);
        }

        /*// 6,创建消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        // 监听器消费者
        messageConsumer.setMessageListener((message) -> {
            if (null != message && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText() + "----");
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (null != message && message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;
                try {
                    System.out.println(mapMessage.getString("k1") + "----" + mapMessage.getStringProperty("property01"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // 需要等待消费者消费，不要急于关闭
        System.in.read();

        // 8，关闭资源
        messageConsumer.close();*/
        session.close();
        connection.close();
    }
}
