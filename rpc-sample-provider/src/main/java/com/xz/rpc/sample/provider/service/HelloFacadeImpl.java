package com.xz.rpc.sample.provider.service;

import com.xz.rpc.provider.annotation.XzService;
import com.xz.rpc.sample.facade.HelloFacade;
import org.springframework.stereotype.Service;

/**
 * @author zhangsong
 */
@Service
@XzService(serviceName = "helloFacade")
public class HelloFacadeImpl implements HelloFacade {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
