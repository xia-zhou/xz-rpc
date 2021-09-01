package com.xz.rpc.sample.provider;

import com.xz.rpc.provider.XzProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author zhangsong
 */
@SpringBootApplication
public class XzRpcSampleProviderApplication {

    @Bean
    public XzProvider init() {
        return new XzProvider();
    }

    public static void main(String[] args) {
        SpringApplication.run(XzRpcSampleProviderApplication.class, args);
    }
}
