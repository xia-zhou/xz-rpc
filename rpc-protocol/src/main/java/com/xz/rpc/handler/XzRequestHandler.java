package com.xz.rpc.handler;

import com.xz.rpc.core.request.XzRequest;
import com.xz.rpc.core.response.XzResponse;
import com.xz.rpc.protocol.XzProtocol;
import com.xz.rpc.protocol.constant.XzMessageStatusEnum;
import com.xz.rpc.protocol.constant.XzMessageTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.cglib.reflect.FastClass;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author zhangsong
 */
public class XzRequestHandler extends SimpleChannelInboundHandler<XzProtocol<XzRequest>> {


    /**
     * 服务名对应的bean
     */
    private Map<String, Object> rpcServiceBeanMap;

    public XzRequestHandler(Map<String, Object> rpcServiceBeanMap) {
        this.rpcServiceBeanMap = rpcServiceBeanMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, XzProtocol<XzRequest> msg) throws Exception {
        Object result = handler(msg.getBody());
        XzProtocol<XzResponse> respProtocol = new XzProtocol<>();
        msg.getHeader().setMessageType((byte) XzMessageTypeEnum.RESPONSE.getType());
        msg.getHeader().setStatus((byte) XzMessageStatusEnum.SUCCESS.getStatus());
        respProtocol.setHeader(msg.getHeader());
        respProtocol.setBody(new XzResponse(result));
        ctx.writeAndFlush(respProtocol);
    }

    /**
     * 处理请求
     *
     * @param request
     * @return
     * @throws InvocationTargetException
     */
    private Object handler(XzRequest request) throws InvocationTargetException {
        Object bean = rpcServiceBeanMap.get(request.getServiceName());
        Class<?> beanClass = bean.getClass();
        FastClass fastClass = FastClass.create(beanClass);
        return fastClass.invoke(fastClass.getIndex(request.getMethodName(), request.getMethodArgSigs()), bean,
                                request.getMethodArgs());
    }
}
