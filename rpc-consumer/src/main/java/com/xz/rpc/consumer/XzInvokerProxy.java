package com.xz.rpc.consumer;

import com.xz.rpc.core.future.XzFuture;
import com.xz.rpc.core.request.XzRequest;
import com.xz.rpc.core.request.XzRequestHolder;
import com.xz.rpc.core.response.XzResponse;
import com.xz.rpc.protocol.XzProtocol;
import com.xz.rpc.protocol.XzProtocolHeader;
import com.xz.rpc.protocol.constant.XzMessageStatusEnum;
import com.xz.rpc.protocol.constant.XzMessageTypeEnum;
import com.xz.rpc.protocol.constant.XzProtocolConstants;
import com.xz.rpc.serialization.XzSerializationTypeEnum;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangsong
 */
public class XzInvokerProxy implements InvocationHandler {

    private String serviceName;

    public XzInvokerProxy(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        XzProtocol<XzRequest> reqProtocol = new XzProtocol<>();
        XzProtocolHeader header = new XzProtocolHeader();
        long requestId = XzRequestHolder.REQUEST_ID_GEN.incrementAndGet();
        header.setMagic(XzProtocolConstants.MAGIC);
        header.setVersion(XzProtocolConstants.VERSION);
        header.setRequestId(requestId);
        header.setSerialization((byte) XzSerializationTypeEnum.JSON.getType());
        header.setMessageType((byte) XzMessageTypeEnum.REQUEST.getType());
        header.setStatus((byte) (XzMessageStatusEnum.SUCCESS.getStatus()));
        reqProtocol.setHeader(header);

        XzRequest requestData = new XzRequest();
        requestData.setServiceName(serviceName);
        requestData.setMethodName(method.getName());
        requestData.setMethodArgSigs(method.getParameterTypes());
        requestData.setMethodArgs(objects);
        reqProtocol.setBody(requestData);
        // 发起调用，并且返回结果
        XzClient xkRpcClient = new XzClient();
        xkRpcClient.sendRequest(reqProtocol);
        XzFuture<XzResponse> future = new XzFuture<>(new DefaultPromise<>(new DefaultEventLoop()), 5000);
        XzRequestHolder.REQUEST_MAP.put(requestId, future);
        return future.getPromise().get(future.getTimeout(), TimeUnit.MILLISECONDS).getAppResponse();
    }
}
