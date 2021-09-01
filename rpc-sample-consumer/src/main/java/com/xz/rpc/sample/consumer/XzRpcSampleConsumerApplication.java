package com.xz.rpc.sample.consumer;

import com.xz.rpc.consumer.XzReferenceBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author zhangsong
 */
@SpringBootApplication
public class XzRpcSampleConsumerApplication {

    @Bean
    public XzReferenceBeanPostProcessor init() {
        return new XzReferenceBeanPostProcessor();
    }

    public static void main(String[] args) {
        SpringApplication.run(XzRpcSampleConsumerApplication.class, args);
    }
}
