package com.xz.rpc.serialization;

import com.xz.rpc.serialization.json.XzJsonSerialization;

/**
 * @author zhangsong
 */
public class XzSerializationFactory {
    /**
     * 获取序列化方法
     *
     * @param serializationType 序列化类型
     * @return
     */
    public static XzSerialization get(byte serializationType) {
        return new XzJsonSerialization();
    }
}
