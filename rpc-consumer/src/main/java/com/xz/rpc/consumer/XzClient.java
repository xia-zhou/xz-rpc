package com.xz.rpc.consumer;

import com.xz.rpc.codec.XzDecoder;
import com.xz.rpc.codec.XzEncoder;
import com.xz.rpc.core.request.XzRequest;
import com.xz.rpc.handler.XzResponseHandler;
import com.xz.rpc.protocol.XzProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangsong
 */
@Slf4j
public class XzClient {
    EventLoopGroup eventLoopGroup;
    Bootstrap bootstrap;

    public XzClient() {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new XzDecoder()).addLast(new XzEncoder()).addLast(
                                new XzResponseHandler());
                    }
                });
    }

    public Object sendRequest(XzProtocol<XzRequest> request) throws InterruptedException {
        XzRequest requestData = request.getBody();
        Object[] params = requestData.getMethodArgs();

        ChannelFuture future = bootstrap.connect("127.0.0.1", 12200).sync();
        future.addListener((ChannelFutureListener) arg0 -> {
            if (future.isSuccess()) {
                log.info("connect rpc server 127.0.0.1 on port 12200 success.");
            } else {
                log.error("connect rpc server 127.0.0.1 on port 12200 failed.");
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        future.channel().writeAndFlush(request);
        return null;
    }

}
