package com.xz.rpc.serialization;

/**
 * @author zhangsong
 */
public enum XzSerializationTypeEnum {

    /**
     * JSON序列化
     */
    JSON(0),
    ;

    private int type;

    public int getType() {
        return type;
    }

    XzSerializationTypeEnum(int type) {
        this.type = type;
    }
}
