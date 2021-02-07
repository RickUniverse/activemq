package org.activemq.enbed;

import org.apache.activemq.broker.BrokerService;

/**
 * @author lijichen
 * @date 2021/2/7 - 20:25
 */
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
