package org.bootmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lijichen
 * @date 2021/2/8 - 14:14
 */
@SpringBootApplication
@EnableScheduling// 开启定时方法
public class MainBootMQ {
    public static void main(String[] args) {
        SpringApplication.run(MainBootMQ.class,args);
    }
}
