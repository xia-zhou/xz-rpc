package com.xz.rpc.handler;

import com.xz.rpc.core.future.XzFuture;
import com.xz.rpc.core.request.XzRequestHolder;
import com.xz.rpc.core.response.XzResponse;
import com.xz.rpc.protocol.XzProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zhangsong
 */
public class XzResponseHandler extends SimpleChannelInboundHandler<XzProtocol<XzResponse>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, XzProtocol<XzResponse> msg) throws Exception {
        long requestId = msg.getHeader().getRequestId();
        XzFuture<XzResponse> future = XzRequestHolder.REQUEST_MAP.remove(requestId);
        future.getPromise().setSuccess(msg.getBody());
    }
}
