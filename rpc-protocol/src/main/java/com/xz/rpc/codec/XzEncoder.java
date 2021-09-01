package com.xz.rpc.codec;

import com.xz.rpc.protocol.XzProtocol;
import com.xz.rpc.protocol.XzProtocolHeader;
import com.xz.rpc.serialization.XzSerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author zhangsong
 */
public class XzEncoder extends MessageToByteEncoder<XzProtocol<Object>> {

    /**
     * +---------------------------------------------------------------+
     * | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
     * +---------------------------------------------------------------+
     * | 状态 1byte |        消息 ID 8byte     |      数据长度 4byte     |
     * +---------------------------------------------------------------+
     */

    @Override
    protected void encode(ChannelHandlerContext ctx, XzProtocol<Object> msg, ByteBuf out) throws Exception {
        XzProtocolHeader header = msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getVersion());
        out.writeByte(header.getSerialization());
        out.writeByte(header.getMessageType());
        out.writeByte(header.getStatus());
        out.writeLong(header.getRequestId());
        byte[] data = XzSerializationFactory.get(header.getSerialization()).serialize(msg.getBody());
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
