package com.zoe.rus.util;

/**
 * @author lpw
 */
public interface Pagination {
    /**
     * 获取每页显示记录数。
     * @return 每页显示记录数。
     */
    int getPageSize();

    /**
     * 获取当前显示页码。
     * @return 当前显示页码。
     */
    int getPageNum();
}
