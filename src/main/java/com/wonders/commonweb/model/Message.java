package com.wonders.commonweb.model;

import lombok.Data;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.model
 * @authorName:wangjiaming
 * @createDate:2020-02-03
 * @editor:IntelliJ IDEA
 * @other:消息返回类
 **/
@Data
public class Message {
    private Boolean status;
    private String msg;

    public Message(Boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
