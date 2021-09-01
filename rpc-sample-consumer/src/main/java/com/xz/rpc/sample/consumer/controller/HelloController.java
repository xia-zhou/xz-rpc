package com.xz.rpc.sample.consumer.controller;

import com.xz.rpc.consumer.annotation.XzReference;
import com.xz.rpc.sample.facade.HelloFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangsong
 */
@RestController
public class HelloController {

    @XzReference(serviceName = "helloFacade")
    private HelloFacade helloFacade;


    @GetMapping("/")
    public String hello1(String name) {
        return helloFacade.hello(name);
    }

}
