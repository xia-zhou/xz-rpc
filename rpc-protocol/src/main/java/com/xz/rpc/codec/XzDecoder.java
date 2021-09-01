package com.xz.rpc.codec;

import com.xz.rpc.core.request.XzRequest;
import com.xz.rpc.core.response.XzResponse;
import com.xz.rpc.protocol.XzProtocol;
import com.xz.rpc.protocol.XzProtocolHeader;
import com.xz.rpc.protocol.constant.XzMessageTypeEnum;
import com.xz.rpc.protocol.constant.XzProtocolConstants;
import com.xz.rpc.serialization.XzSerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zhangsong
 */
public class XzDecoder extends ByteToMessageDecoder {

    /**
     * +---------------------------------------------------------------+
     * | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
     * +---------------------------------------------------------------+
     * | 状态 1byte |        消息 ID 8byte     |      数据长度 4byte     |
     * +---------------------------------------------------------------+
     */

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < XzProtocolConstants.HEADER_LENGTH) {
            return;
        }
        in.markReaderIndex();
        short magic = in.readShort();
        if (magic != XzProtocolConstants.MAGIC) {
            throw new IllegalArgumentException("message not start fix magic:" + magic);
        }
        byte version = in.readByte();
        byte serializeType = in.readByte();
        byte msgType = in.readByte();
        byte status = in.readByte();
        long requestId = in.readLong();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        XzMessageTypeEnum messageTypeEnum = XzMessageTypeEnum.parse(msgType);
        if (messageTypeEnum == null) {
            return;
        }
        XzProtocolHeader header = new XzProtocolHeader(magic, version, serializeType, msgType, status, requestId,
                                                       dataLength);
        switch (messageTypeEnum) {
            case REQUEST:
                XzProtocol<XzRequest> protocol = new XzProtocol<>();
                protocol.setHeader(header);
                protocol.setBody(XzSerializationFactory.get(serializeType).deserialize(data, XzRequest.class));
                out.add(protocol);
                break;
            case RESPONSE:
                XzProtocol<XzResponse> resProtocol = new XzProtocol<>();
                resProtocol.setHeader(header);
                resProtocol.setBody(XzSerializationFactory.get(serializeType).deserialize(data, XzResponse.class));
                out.add(resProtocol);
                break;
            default:
                break;
        }
    }
}
