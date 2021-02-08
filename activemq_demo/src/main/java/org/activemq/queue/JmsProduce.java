package org.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

/**
 * @author lijichen
 * @date 2021/2/7 - 15:33
 */
public class JmsProduce {

    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号，如果用自己的需要改动
//    public static final String ACTIVEMQ_URI = "tcp://192.168.43.154:61616";
    /**
     * 使用的时nioauto配置
     * tcp 和 nio都可以使用
     */
    public static final String ACTIVEMQ_URI = "tcp://192.168.43.154:61608";
    public static final String QUEUE_NAME = "queue01_transport_protocol_nio——JDBC";


    public static void main(String[] args) throws JMSException {

        // 1,创建默认默认用户名密码的连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URI);
        // 开启异步传递，可能会导致消息丢失，因此需要接收回调
        activeMQConnectionFactory.setUseAsyncSend(true);

        // 2,获取连接
        Connection connection = activeMQConnectionFactory.createConnection();
        // 3，开启连接
        connection.start();
        // 4，创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5，创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        // 6，创建生产者
        ActiveMQMessageProducer messageProducer = (ActiveMQMessageProducer) session.createProducer(queue);
        // 持久化消息，默认是DeliveryMode.PERSISTENT
        // 数据库连接时必须是；PERSISTENT,否则数据库中没有数据
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        /**
         *
         * 延迟和定时投递看笔记
         *
         */


        TextMessage textMessage = null;
        // 7,发送三条消息到MQ队列中
        for (int i = 0; i < 30; i++) {
            // 8，创建消息
            textMessage = session.createTextMessage("spend message :" + i);
            textMessage.setJMSMessageID(UUID.randomUUID().toString() + "order_OK");
            String jmsMessageID = textMessage.getJMSMessageID();
            // 9, 发送给MQ
            // 接受回调！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！1！！！！！！！！！！！！！！！1
            messageProducer.send(textMessage,new AsyncCallback(){

                // 走onException 方法表示成功
                @Override
                public void onException(JMSException e) {
                    System.out.println(jmsMessageID + "失败！");
                }

                // 走onSuccess 方法表示失败
                @Override
                public void onSuccess() {
                    System.out.println(jmsMessageID + "成功！");
                }
            });
        }
        // 10，关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("消息发送完成");
    }
}
