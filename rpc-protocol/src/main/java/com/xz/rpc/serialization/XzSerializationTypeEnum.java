package com.xz.rpc.serialization;

/**
 * @author zhangsong
 */
public enum XzSerializationTypeEnum {

    /**
     * JSON序列化
     */
    JSON(0),

    /**
     * PROTOSTUFF序列化方式
     */
    PROTOSTUFF(1),
    ;

    private int type;

    public int getType() {
        return type;
    }

    XzSerializationTypeEnum(int type) {
        this.type = type;
    }
}
