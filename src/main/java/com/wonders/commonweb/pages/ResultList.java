package com.wonders.commonweb.pages;

import java.io.Serializable;
import java.util.List;

/**
 * @projectName:repeat-message
 * @packageName:com.wonders.repeatmessage.pages
 * @authorName:wangjiaming
 * @createDate:2019-01-03
 * @editor:IntelliJ IDEA
 * @other:
 **/
public class ResultList implements Serializable {

    private long total;

    private List<?> rows;

    /**
     * @param total
     * @param rows
     */
    public ResultList(long total, List<?> rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }


}
