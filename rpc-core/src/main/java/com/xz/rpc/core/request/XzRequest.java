package com.xz.rpc.core.request;

import java.io.Serializable;

/**
 * @author zhangsong
 */
public class XzRequest implements Serializable {

    /**
     * 要调用的服务名
     */
    private String serviceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 请求参数签名
     */
    private Class<?>[] methodArgSigs;

    /**
     * 请求参数
     */
    private Object[] methodArgs;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getMethodArgSigs() {
        return methodArgSigs;
    }

    public void setMethodArgSigs(Class<?>[] methodArgSigs) {
        this.methodArgSigs = methodArgSigs;
    }

    public Object[] getMethodArgs() {
        return methodArgs;
    }

    public void setMethodArgs(Object[] methodArgs) {
        this.methodArgs = methodArgs;
    }
}
