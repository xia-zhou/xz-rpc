package com.xz.rpc.provider;

import com.xz.rpc.codec.XzDecoder;
import com.xz.rpc.codec.XzEncoder;
import com.xz.rpc.handler.XzRequestHandler;
import com.xz.rpc.provider.annotation.XzService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangsong
 */
@Slf4j
public class XzProvider implements InitializingBean, BeanPostProcessor {


    private Map<String, Object> serviceMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        new Thread(() -> {
            try {
                EventLoopGroup boss = new NioEventLoopGroup();
                EventLoopGroup work = new NioEventLoopGroup();
                try {
                    ServerBootstrap serverBootstrap = new ServerBootstrap();
                    serverBootstrap.group(boss, work).channel(NioServerSocketChannel.class).childOption(
                            ChannelOption.SO_KEEPALIVE, true).childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new XzEncoder()).addLast(new XzDecoder()).addLast(
                                    new XzRequestHandler(serviceMap));
                        }
                    });
                    ChannelFuture channelFuture = serverBootstrap.bind("127.0.0.1", 12200).sync();
                    log.info("server addr 127.0.0.1 started on port 12200");
                    channelFuture.channel().closeFuture().sync();
                } finally {
                    boss.shutdownGracefully();
                    work.shutdownGracefully();
                }
            } catch (Exception e) {
                log.error("start rpc server error.", e);
            }
        }).start();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        XzService xzService = bean.getClass().getAnnotation(XzService.class);
        if (xzService == null) {
            return bean;
        }
        serviceMap.put(xzService.serviceName(), bean);
        return bean;
    }
}
