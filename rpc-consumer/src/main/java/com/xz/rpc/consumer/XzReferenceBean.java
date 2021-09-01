package com.xz.rpc.consumer;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author zhangsong
 */
public class XzReferenceBean implements FactoryBean<Object> {

    /**
     * 接口类名
     */
    private Class<?> interfaceType;

    private String serviceName;
    /**
     * 实际的bean
     */
    private Object object;

    public void setInterfaceType(Class<?> interfaceType) {
        this.interfaceType = interfaceType;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public Object getObject() throws Exception {
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceType;
    }

    public void init() {
        this.object = Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class<?>[]{interfaceType},
                                             new XzInvokerProxy(serviceName));
    }
}
