package com.wonders.commonweb.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.utils
 * @authorName:wangjiaming
 * @createDate:2020-02-25
 * @editor:IntelliJ IDEA
 * @other:
 **/
public class DataTrans {

    public static List<List<String>> ListMapTransDoubleList(List<Map<String,Object>> inputData){
        List<List<String>> outputData=new ArrayList<>();
        for(Map<String,Object> rows:inputData){
            List<String> rowData=new ArrayList<>();
            for(String key:rows.keySet()){
                rowData.add(rows.get(key).toString());
            }
            outputData.add(rowData);
        }
        return outputData;
    }

}
