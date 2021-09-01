package com.xz.rpc.protocol;

import java.io.Serializable;

/**
 * @author zhangsong
 */
public class XzProtocol<T> implements Serializable {

    /**
     * 协议头
     */
    private XzProtocolHeader header;

    /**
     * 协议体
     */
    private T body;

    public XzProtocol() {
    }

    public XzProtocol(XzProtocolHeader header, T body) {
        this.header = header;
        this.body = body;
    }

    public XzProtocolHeader getHeader() {
        return header;
    }

    public void setHeader(XzProtocolHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
