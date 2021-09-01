package com.xz.rpc.core.request;

import com.xz.rpc.core.future.XzFuture;
import com.xz.rpc.core.response.XzResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhangsong
 */
public class XzRequestHolder {

    /**
     * request Id生成器
     */
    public static AtomicLong REQUEST_ID_GEN = new AtomicLong(0);

    /**
     * 请求id对应的请求结果
     */
    public static final Map<Long, XzFuture<XzResponse>> REQUEST_MAP = new ConcurrentHashMap<>();
}
