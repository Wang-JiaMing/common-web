package com.wonders.commonweb.core.model.common;

import lombok.Data;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.model.common
 * @authorName:wangjiaming
 * @createDate:2019-08-29
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Data
public class CommonModel {

    /**
     * 行数
     */
    private String limit;
    /**
     * 页码
     */
    private String offset;

}
