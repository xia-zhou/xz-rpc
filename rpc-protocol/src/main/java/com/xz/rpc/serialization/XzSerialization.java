package com.xz.rpc.serialization;

import java.io.IOException;

/**
 * @author zhangsong
 */
public interface XzSerialization {

    /**
     * 序列化接口
     *
     * @param obj 要序列化的对象
     * @param <T>
     * @return 序列化的二进制结果
     * @throws IOException
     */
    <T> byte[] serialize(T obj) throws IOException;

    /**
     * 反序列化接口
     *
     * @param data 二进制数据
     * @param clz  要反序列化的class
     * @param <T>
     * @return 反序列化的对象
     * @throws IOException
     */
    <T> T deserialize(byte[] data, Class<T> clz) throws IOException;
}
