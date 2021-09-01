package com.xz.rpc.core.response;

import java.io.Serializable;

/**
 * @author zhangsong
 */
public class XzResponse implements Serializable {

    /**
     * 返回结果
     */
    private Object appResponse;

    public XzResponse() {
    }

    public XzResponse(Object appResponse) {
        this.appResponse = appResponse;
    }

    public Object getAppResponse() {
        return appResponse;
    }

    public void setAppResponse(Object appResponse) {
        this.appResponse = appResponse;
    }
}
