package com.xz.rpc.protocol;

import java.io.Serializable;

/**
 * @author zhangsong
 */
public class XzProtocolHeader implements Serializable {
    /**
     +---------------------------------------------------------------+
     | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
     +---------------------------------------------------------------+
     | 状态 1byte |        消息 ID 8byte     |      数据长度 4byte     |
     +---------------------------------------------------------------+
     */

    /**
     * 魔数
     */
    private short magic;

    /**
     * 版本
     */
    private byte version;

    /**
     * 序列化算法
     */
    private byte serialization;

    /**
     * 消息类型
     */
    private byte messageType;

    /**
     * 消息状态
     */
    private byte status;

    /**
     * 消息唯一id
     */
    private long requestId;

    /**
     * 消息体长度
     */
    private int dataLength;

    public XzProtocolHeader() {
    }

    public XzProtocolHeader(short magic, byte version, byte serialization, byte messageType, byte status,
                            long requestId, int dataLength) {
        this.magic = magic;
        this.version = version;
        this.serialization = serialization;
        this.messageType = messageType;
        this.status = status;
        this.requestId = requestId;
        this.dataLength = dataLength;
    }

    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getSerialization() {
        return serialization;
    }

    public void setSerialization(byte serialization) {
        this.serialization = serialization;
    }

    public byte getMessageType() {
        return messageType;
    }

    public void setMessageType(byte messageType) {
        this.messageType = messageType;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }
}
