package com.xz.rpc.sample.facade.module;

import lombok.Data;

/**
 * @author zhangsong
 */
@Data
public class HelloResult {

    private String response;

    public HelloResult(String response) {
        this.response = response;
    }
}
