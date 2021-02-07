package org.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 如果是两个消费者则使用负载均衡策略
 * @author lijichen
 * @date 2021/2/7 - 15:57
 */
public class JmsConsumer {

    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号，如果用自己的需要改动
    public static final String ACTIVEMQ_URI = "tcp://192.168.43.154:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {
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

        // 6,创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        /*while (true) {
            // 7,接收消息，如果为空没有消息会一直阻塞
            TextMessage receive = (TextMessage) messageConsumer.receive(3000);
            if (null != receive) {
                System.out.println(receive.getText() + "----");
            } else {
                break;
            }
        }*/

        // 监听器消费者
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (null != message && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(textMessage.getText() + "----");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // 需要等待消费者消费，不要急于关闭
        System.in.read();

        // 8，关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
