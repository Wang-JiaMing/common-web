package com.yzyl.commonweb.demo.model;

import com.yzyl.commonweb.core.model.common.CommonModel;
import lombok.Data;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.model
 * @authorName:wangjiaming
 * @createDate:2019-08-29
 * @editor:IntelliJ IDEA
 * @other: 由于引入了lombok，所以不需要生成get/set方法
 **/
@Data
public class DemoTable extends CommonModel {

    private String owner;
    private String tableName;
    private String tablespaceName;

}
