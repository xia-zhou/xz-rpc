package com.xz.rpc.sample.facade;

import com.xz.rpc.sample.facade.module.HelloParams;
import com.xz.rpc.sample.facade.module.HelloResult;

/**
 * @author zhangsong
 */
public interface HelloFacade {

    /**
     * say  hello
     *
     * @param name 拼接字符串使用
     * @return 拼接结果
     */
    String hello(String name);

    /**
     * say hello
     *
     * @param params
     * @return
     */
    HelloResult hello(HelloParams params);

}
