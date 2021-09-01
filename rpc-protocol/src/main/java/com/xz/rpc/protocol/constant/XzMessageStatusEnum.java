package com.xz.rpc.protocol.constant;

/**
 * @author zhangsong
 */
public enum XzMessageStatusEnum {

    /**
     * 成功
     */
    SUCCESS(0),

    /**
     * 失败
     */
    FAIL(1);

    private int status;

    public int getStatus() {
        return status;
    }

    XzMessageStatusEnum(int status) {
        this.status = status;
    }
}
