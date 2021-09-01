package com.xz.rpc.protocol.constant;

import java.util.Arrays;

/**
 * @author zhangsong
 */
public enum XzMessageTypeEnum {

    /**
     * 请求
     */
    REQUEST(1),

    /**
     * 响应
     */
    RESPONSE(2),

    /**
     * 心跳
     */
    HEARTBEAT(3);

    private int type;

    public int getType() {
        return type;
    }

    XzMessageTypeEnum(int type) {
        this.type = type;
    }

    public static XzMessageTypeEnum parse(short type) {
        return Arrays.stream(values()).filter(value -> value.getType() == type).findFirst().orElse(null);
    }
}
