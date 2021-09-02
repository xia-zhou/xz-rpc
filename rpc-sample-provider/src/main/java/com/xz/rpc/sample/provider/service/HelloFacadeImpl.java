package com.xz.rpc.sample.provider.service;

import com.xz.rpc.provider.annotation.XzService;
import com.xz.rpc.sample.facade.HelloFacade;
import com.xz.rpc.sample.facade.module.HelloParams;
import com.xz.rpc.sample.facade.module.HelloResult;
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

    @Override
    public HelloResult hello(HelloParams params) {
        return new HelloResult("hello " + params.getName());
    }
}
